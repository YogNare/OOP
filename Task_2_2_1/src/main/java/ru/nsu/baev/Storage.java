package ru.nsu.baev;

import java.util.LinkedList;
import java.util.Queue;

public class Storage {
    private Integer capacity;
    private final Queue<Order> pizzaList = new LinkedList<Order>();
    private final Queue<Order> newOrderList = new LinkedList<Order>();

    public Storage(Integer cp) {
        capacity = cp;
    }

    public synchronized int putPizza(Order order) {
        if (pizzaList.size() < capacity) {
            pizzaList.add(order);
            order.putToStorage();

            return 0;
        } else {
            return 1;
        }
    }

    public synchronized Order getPizza() {
        if (!pizzaList.isEmpty()) {
            Order order = pizzaList.remove();
            order.takeCourier();
            return order;
        }

        return null;
    }

    public synchronized Order getOrder() {
        if (!newOrderList.isEmpty()) {
            Order order = newOrderList.remove();
            order.putToBaker();
            return order;
        }

        return null;
    }

    public synchronized void putOrder(Order order) {
        newOrderList.add(order);
        order.addToQueue();
    }
}
