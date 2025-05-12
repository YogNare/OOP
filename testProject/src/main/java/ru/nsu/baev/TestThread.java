package ru.nsu.baev;

public class TestThread implements Runnable {

    public void printOne() throws InterruptedException {
        System.out.println("1");
        Thread.sleep(1000);
    }

    @Override
    public void run() {

    }
}
