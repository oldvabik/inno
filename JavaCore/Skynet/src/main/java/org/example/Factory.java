package org.example;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Factory implements Runnable {
    private final BlockingQueue<RobotParts> storage;
    private final Random random;
    private int daysPassed;
    private static final int MAX_PARTS_PER_DAY = 10;
    private static final int TOTAL_DAYS = 100;

    public Factory() {
        this.storage = new LinkedBlockingQueue<>();
        this.random = new Random();
        this.daysPassed = 0;
    }

    public BlockingQueue<RobotParts> getStorage() {
        return storage;
    }

    @Override
    public void run() {
        while (daysPassed < TOTAL_DAYS) {
            try {
                // Work during the day
                Thread.sleep(100); // Simulate work day

                int partsToProduce = random.nextInt(MAX_PARTS_PER_DAY) + 1;
                for (int i = 0; i < partsToProduce; i++) {
                    RobotParts part = RobotParts.values()[random.nextInt(RobotParts.values().length)];
                    storage.put(part);
                }

                daysPassed++;
                System.out.println("Day " + daysPassed + ": Factory produced " + partsToProduce + " parts. Total in storage: " + storage.size());

                // Night - factory doesn't work
                Thread.sleep(50);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println("Factory finished work after " + TOTAL_DAYS + " days");
    }
}