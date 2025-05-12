package ru.nsu.baev;

public class Client extends Thread {
    int timeSleep;
    Storage storage;
    int personalId;
    int subId = 0;
    int x;
    int y;
    private long endTime;

    public Client(int timeSleep, Storage storage, int personalId, int x, int y, long endTime) {
        this.timeSleep = timeSleep;
        this.storage = storage;
        this.personalId = personalId;
        this.x = x;
        this.y = y;
        this.endTime = endTime;
    }

    private int idGenerator() {
        return personalId * 1000 + subId++;
    }

    @Override
    public void run() {
        while (System.currentTimeMillis() < endTime) {
            try {
                Thread.sleep(timeSleep);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (System.currentTimeMillis() >= endTime) {
                break;
            }
            Order order = new Order(idGenerator(), x, y);
            storage.putOrder(order);
        }
    }

}
