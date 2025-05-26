package ru.nsu.baev.engine;

import ru.nsu.baev.model.*;

import java.util.*;
import java.util.concurrent.*;

public class GameEngine {

    private final Map<UUID, Player> players = new ConcurrentHashMap<>();
    private final List<Point> food = new ArrayList<>();
    private final int width = 40;
    private final int height = 40;

    public void addPlayer(Player player) {
        players.put(player.id, player);
    }

    public void removePlayer(UUID id) {
        players.remove(id);
    }

    public boolean checkCollisionByYourself(Player player, Direction direction) {
        if (player.snake.size() < 2) return true;

        Point tmpPoint = nextStep(player, direction);

        return !tmpPoint.equals(player.snake.get(1));
    }

    public void updateDirection(UUID id, Direction direction) {
        Player player = players.get(id);
        if (player != null && player.alive && checkCollisionByYourself(player, direction)) {
            player.direction = direction;
        }
    }

    public void gameLoop() {
        for (Player player : players.values()) {
            if (!player.alive) continue;
            if (checkCollision(player)) continue;
            movePlayer(player);
            eatFood(player);
        }

        while (food.size() < 5) {
            food.add(new Point((int)(Math.random()*width), (int)(Math.random()*height)));
        }
    }

    public Point nextStep(Player player, Direction direction) {
        Point head = player.getHead();
        int x = head.x;
        int y = head.y;

        switch (direction) {
            case UP:    y -= 1; break;
            case DOWN:  y += 1; break;
            case LEFT:  x -= 1; break;
            case RIGHT: x += 1; break;
        }

        return new Point(x, y);
    }

    private void movePlayer(Player player) {

        Point newHead = nextStep(player, player.direction);
        player.snake.addFirst(newHead);
        player.snake.removeLast();
    }

    private void eatFood(Player player) {
        Point head = player.getHead();
        for (int i = 0; i < food.size(); i++) {
            if (head.equals(food.get(i))) {
                food.remove(i);
                Point tmpPoint = player.snake.getLast();
                player.snake.addLast(new Point(tmpPoint.x, tmpPoint.y));
                player.score += 1;
            }
        }
    }

    private boolean checkCollision(Player player) {
        Point tmpPoint = nextStep(player, player.direction);

        if (tmpPoint.x < 0 || tmpPoint.y < 0 || tmpPoint.x >= width || tmpPoint.y >= height) {
            player.alive = false;
            return true;
        }

        for (Player other : players.values()) {
            for (Point p : other.snake) {
                if (p != tmpPoint && p.equals(tmpPoint)) {
                    player.alive = false;
                    return true;
                }
            }
        }

        return false;
    }

    public GameState getStateSnapshot() {
        GameState state = new GameState();
        state.players.putAll(players);
        state.food.addAll(food);
        return state;
    }
}
