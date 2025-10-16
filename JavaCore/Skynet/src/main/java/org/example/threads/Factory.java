package org.example.threads;

import org.example.enums.RobotParts;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Factory extends Thread {
    private final BlockingQueue<RobotParts> partsQueue = new LinkedBlockingQueue<>();
    private final Random random = new Random();
    private int daysCompleted = 0;
    private volatile boolean simulationRunning = true;

    private volatile boolean isNight = false;
    private final Object lock = new Object();
    private int factionsCollected = 0;
    private final int totalFactions = 2;

    public Factory() {
        setName("FactoryThread");
    }

    public boolean isRunning() {
        return simulationRunning;
    }

    public int getRemainingParts() {
        return partsQueue.size();
    }

    public int getDaysCompleted() {
        return daysCompleted;
    }

    @Override
    public void run() {
        try {
            for (int day = 1; day <= 100; day++) {
                produceParts(day);

                startNightPhase();

                waitForAllFactionsToCollect();

                endNightPhase();
            }

            stopSimulation();
            System.out.println("\nFactory: 100 days completed.");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void produceParts(int day) throws InterruptedException {
        int partsCount = random.nextInt(11);
        int[] produced = new int[4];

        for (int i = 0; i < partsCount; i++) {
            RobotParts part = RobotParts.values()[random.nextInt(4)];
            partsQueue.put(part);
            produced[part.ordinal()]++;
        }

        daysCompleted++;

        System.out.printf("Day %d: Produced %d parts (H:%d, T:%d, Ha:%d, F:%d)%n",
                daysCompleted, partsCount, produced[0], produced[1], produced[2], produced[3]);

        Thread.sleep(50);
    }

    private void startNightPhase() {
        synchronized (lock) {
            isNight = true;
            factionsCollected = 0;
            lock.notifyAll();
        }
    }

    private void waitForAllFactionsToCollect() throws InterruptedException {
        synchronized (lock) {
            while (factionsCollected < totalFactions && simulationRunning) {
                lock.wait();
            }
        }
        Thread.sleep(20);
    }

    private void endNightPhase() {
        synchronized (lock) {
            isNight = false;
            lock.notifyAll();
        }
    }

    public List<RobotParts> takeParts() throws InterruptedException {
        synchronized (lock) {
            while (!isNight && simulationRunning) {
                lock.wait();
            }

            if (!simulationRunning && partsQueue.isEmpty()) {
                return Collections.emptyList();
            }
        }

        List<RobotParts> taken = new ArrayList<>();
        int maxTake = Math.min(5, partsQueue.size());

        for (int i = 0; i < maxTake; i++) {
            RobotParts part = partsQueue.poll();
            if (part != null) {
                taken.add(part);
            }
        }

        synchronized (lock) {
            factionsCollected++;
            if (factionsCollected == totalFactions) {
                lock.notifyAll();
            }
        }

        return taken;
    }

    public void waitForDay() throws InterruptedException {
        synchronized (lock) {
            while (isNight && simulationRunning) {
                lock.wait();
            }
        }
    }

    public void stopSimulation() {
        simulationRunning = false;
        synchronized (lock) {
            lock.notifyAll();
        }
    }
}