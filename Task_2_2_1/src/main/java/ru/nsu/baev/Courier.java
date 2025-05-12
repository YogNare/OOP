package ru.nsu.baev;

import java.util.LinkedList;
import java.util.Queue;

public class Courier extends Thread {
    private final Storage storage;
    private final int capacity;
    private final Queue<Order> queue = new LinkedList<>();
    private int personalX;
    private int personalY;
    private long endTime;

    public Courier(Storage storage, int capacity, long endTime) {
        this.storage = storage;
        this.capacity = capacity;
        this.endTime = endTime;
    }

    private int rideTime(Order order) {
        double time = Math.sqrt(Math.pow(((double) order.x - personalX), 2) + Math.pow(((double) order.y - personalY), 2));
        return Math.toIntExact(Math.round(time));
    }

    private int timeToHome() {
        double time = Math.sqrt(Math.pow(((double) 500 - personalX), 2) + Math.pow(((double) 500 - personalY), 2));
        return Math.toIntExact(Math.round(time));
    }

    @Override
    public void run() {
        while (System.currentTimeMillis() < endTime) {
	    
            for (int i = 0; i < capacity; i++) {
                Order order = storage.getPizza();
                if (order != null) {
                    queue.add(order);
                } else {
                    break;
                }
            }
            if (queue.isEmpty()) {
                continue;
            }

            personalX = 500;
            personalY = 500;

            Order order;
            do {
                order = queue.remove();
                try {
                    Thread.sleep(rideTime(order));
                    personalX = order.x;
                    personalY = order.y;
                    order.delivered();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } while (!queue.isEmpty());

            if (System.currentTimeMillis() >= endTime) {
                break;
            }
            try {
                Thread.sleep(timeToHome());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
