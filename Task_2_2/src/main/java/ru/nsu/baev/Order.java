package ru.nsu.baev;

public class Order {
    private static int counter = 1;
    private final int id;

    public Order() {
        this.id = counter++;
    }

    public int getId() {
        return id;
    }
}

