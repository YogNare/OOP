package ru.nsu.baev.handler;

import ru.nsu.baev.engine.GameEngine;
import ru.nsu.baev.model.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;
import java.util.concurrent.*;

public class SnakeHandler extends TextWebSocketHandler {

    private static final GameEngine engine = new GameEngine();
    private static final Map<WebSocketSession, UUID> sessions = new ConcurrentHashMap<>();
    private static final ObjectMapper mapper = new ObjectMapper();

    private static final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    static {
        executor.scheduleAtFixedRate(() -> {
            engine.gameLoop();
            GameState state = engine.getStateSnapshot();
            String json;
            try {
                json = mapper.writeValueAsString(state);
                for (WebSocketSession session : sessions.keySet()) {
                    session.sendMessage(new TextMessage(json));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 75, TimeUnit.MILLISECONDS);
    }

//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        UUID id = UUID.randomUUID();
//        Player player = new Player(id, "", new Random().nextInt(20), new Random().nextInt(20));
//        engine.addPlayer(player);
//        sessions.put(session, id);
//    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        UUID id = sessions.get(session);
        Map<String, Object> msg = mapper.readValue(message.getPayload(), Map.class);

        switch ((String) msg.get("type")) {
            case "join" -> {
                String name = (String) msg.get("name");
                UUID playerId = UUID.randomUUID();
                Player player = new Player(playerId, name, new Random().nextInt(20), new Random().nextInt(20), false);
                engine.addPlayer(player);
                sessions.put(session, playerId);
            }
            case "move" -> {
                if (id != null) {
                    String dir = (String) msg.get("direction");
                    try {
                        engine.updateDirection(id, Direction.valueOf(dir));
                    } catch (IllegalArgumentException ignored) {}
                }
            }
        }
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        UUID id = sessions.remove(session);
        engine.removePlayer(id);
    }
}
