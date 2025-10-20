package org.example.threads;

import org.example.enums.RobotParts;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Faction extends Thread {
    private final String name;
    private final AtomicInteger[] parts = new AtomicInteger[4];
    private final AtomicInteger totalCollected = new AtomicInteger(0);
    private final Factory factory;

    public Faction(String name, Factory factory) {
        this.name = name;
        this.factory = factory;
        for (int i = 0; i < parts.length; i++) {
            parts[i] = new AtomicInteger(0);
        }
        setName(name + "Thread");
    }

    public void addPart(RobotParts part) {
        parts[part.ordinal()].incrementAndGet();
        totalCollected.incrementAndGet();
    }

    public int calculateCompleteRobots() {
        int heads = parts[0].get();
        int torsos = parts[1].get();
        int hands = parts[2].get();
        int foot = parts[3].get();

        return Math.min(Math.min(heads, torsos), Math.min(hands / 2, foot / 2));
    }

    public void printStatus() {
        int robots = calculateCompleteRobots();
        System.out.printf("%s: %d robots (Heads: %d, Torsos: %d, Hands: %d, Foot: %d, Total: %d)%n",
                name, robots, parts[0].get(), parts[1].get(), parts[2].get(), parts[3].get(), totalCollected.get());
    }

    @Override
    public void run() {
        try {
            while (factory.isRunning() || factory.getRemainingParts() > 0) {
                List<RobotParts> collected = factory.takeParts();
                for (RobotParts part : collected) {
                    addPart(part);
                }

                factory.waitForDay();
            }
        } catch (InterruptedException e) {
            // Normal completion
        }
    }
}