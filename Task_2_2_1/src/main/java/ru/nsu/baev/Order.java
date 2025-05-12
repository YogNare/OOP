package ru.nsu.baev;

public class Order {
    private final int id;
    int x;
    int y;

    public Order(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public void addToQueue() {
        System.out.println(id + " - received");
    }

    public void putToBaker() {
        System.out.println(id + " - baked");
    }

    public void putToStorage() {
        System.out.println(id + " - placed in storage");
    }

    public void takeCourier() {
        System.out.println(id + " - transferred to delivery");
    }

    public void delivered() {
        System.out.println(id + " - delivered");
    }

}

