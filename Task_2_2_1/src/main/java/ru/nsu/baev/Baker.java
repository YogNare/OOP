package ru.nsu.baev;

public class Baker extends Thread {
    private final Storage storage;
    private final long workTime;
    private long endTime;

    public Baker(long workTime, Storage storage, long endTime) {
        this.workTime = workTime;
        this.storage = storage;
        this.endTime = endTime;
    }

    @Override
    public void run() {
        boolean goOut = false;
        while (System.currentTimeMillis() < endTime) {
            Order order = null;
            do {
                if (System.currentTimeMillis() >= endTime) {
                    goOut = true;
                    break;
                }
                order = storage.getOrder();
            } while (order == null);

            if (goOut) {
                break;
            }

            if (System.currentTimeMillis() >= endTime) {
                storage.putOrder(order);
                break;
            }

            try {
                Thread.sleep(workTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (System.currentTimeMillis() >= endTime) {
                storage.putOrder(order);
                break;
            }

            int success;
            do {
                success = storage.putPizza(order);
                if (System.currentTimeMillis() >= workTime) {
                    break;
                }
            } while (success != 0);

        }
    }
}
