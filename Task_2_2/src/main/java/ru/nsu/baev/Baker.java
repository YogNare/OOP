package ru.nsu.baev;

public class Baker extends Thread {
    private final int id;
    private final int speed;
    private final Storage storage;
    private boolean running = true;

    public Baker(int id, int speed, Storage storage) {
        this.id = id;
        this.speed = speed;
        this.storage = storage;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Order order = new Order();
                System.out.println("[" + order.getId() + "] Пекарь " + id + " начал готовить");
                Thread.sleep(speed);  // Симуляция готовки
                System.out.println("[" + order.getId() + "] Пекарь " + id + " закончил готовить");
                storage.addPizza(order);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void stopBaking() {
        running = false;
    }
}
