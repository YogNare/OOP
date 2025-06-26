package ru.nsu.baev.model;

import java.util.*;

public class GameState {
    public Map<UUID, Player> players = new HashMap<>();
    public List<Point> food = new ArrayList<>();
}

