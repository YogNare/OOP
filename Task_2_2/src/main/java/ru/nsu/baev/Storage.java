package ru.nsu.baev;

import java.util.LinkedList;
import java.util.Queue;

public class Storage {
    private final int capacity;
    private final Queue<Order> pizzas = new LinkedList<>();

    public Storage(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void addPizza(Order order) throws InterruptedException {
        while (pizzas.size() >= capacity) {
            wait();  // Ждем освобождения места
        }
        pizzas.add(order);
        System.out.println("[" + order.getId() + "] Готова и помещена на склад");
        notifyAll();
    }

    public synchronized Order[] takePizzas(int maxCount) throws InterruptedException {
        while (pizzas.isEmpty()) {
            wait();  // Ждем появления пицц
        }
        int count = Math.min(maxCount, pizzas.size());
        Order[] taken = new Order[count];
        for (int i = 0; i < count; i++) {
            taken[i] = pizzas.poll();
        }
        notifyAll();
        return taken;
    }
}
