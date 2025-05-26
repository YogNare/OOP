package ru.nsu.baev.model;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Player {
    public UUID id;
    public String name;
    public List<Point> snake = new LinkedList<>();
    public Direction direction = Direction.RIGHT;
    public boolean alive = true;
    public int score = 0;
    public boolean isBot = false;

    public Player(UUID id, String name, int startX, int startY, boolean isBot) {
        this.id = id;
        this.name = name;
        this.isBot = isBot;
        snake.add(new Point(startX, startY));
    }

    public Point getHead() {
        return snake.get(0);
    }
}

