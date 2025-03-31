package ru.nsu.baev;

public class Courier extends Thread {
    private final int id;
    private final int capacity;
    private final Storage storage;
    private boolean running = true;

    public Courier(int id, int capacity, Storage storage) {
        this.id = id;
        this.capacity = capacity;
        this.storage = storage;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Order[] orders = storage.takePizzas(capacity);
                System.out.print("[");
                for (Order order : orders) {
                    System.out.print(order.getId() + " ");
                }
                System.out.println("] Курьер " + id + " забрал для доставки");
                Thread.sleep(3000);  // Симуляция доставки
                System.out.println("Курьер " + id + " доставил заказы");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void stopDelivering() {
        running = false;
    }
}
