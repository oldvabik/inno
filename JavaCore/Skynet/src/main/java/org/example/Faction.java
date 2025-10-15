package org.example;

import java.util.*;
import java.util.concurrent.BlockingQueue;

public class Faction implements Runnable {
    private final String name;
    private final BlockingQueue<RobotParts> factoryStorage;
    private final Map<RobotParts, Integer> inventory;
    private final Random random;
    private int robotsBuilt;
    private static final int MAX_PARTS_PER_NIGHT = 5;

    public Faction(String name, BlockingQueue<RobotParts> factoryStorage) {
        this.name = name;
        this.factoryStorage = factoryStorage;
        this.inventory = new HashMap<>();
        this.random = new Random();
        this.robotsBuilt = 0;

        for (RobotParts part : RobotParts.values()) {
            inventory.put(part, 0);
        }
    }

    private boolean canBuildRobot() {
        return inventory.get(RobotParts.HEAD) >= 1 &&
                inventory.get(RobotParts.TORSO) >= 1 &&
                inventory.get(RobotParts.HAND) >= 2 &&
                inventory.get(RobotParts.FEET) >= 2;
    }

    private void buildRobot() {
        if (canBuildRobot()) {
            inventory.put(RobotParts.HEAD, inventory.get(RobotParts.HEAD) - 1);
            inventory.put(RobotParts.TORSO, inventory.get(RobotParts.TORSO) - 1);
            inventory.put(RobotParts.HAND, inventory.get(RobotParts.HAND) - 2);
            inventory.put(RobotParts.FEET, inventory.get(RobotParts.FEET) - 2);
            robotsBuilt++;
            System.out.println(name + " built a robot! Total robots: " + robotsBuilt);
        }
    }

    @Override
    public void run() {
        try {
            for (int day = 0; day < 100; day++) {
                // Day - faction builds robots from available parts
                buildRobot();

                // Night - collect parts from factory
                Thread.sleep(150); // Wait for night

                int partsTaken = 0;
                while (partsTaken < MAX_PARTS_PER_NIGHT && !factoryStorage.isEmpty()) {
                    RobotParts part = factoryStorage.poll();
                    if (part != null) {
                        inventory.put(part, inventory.get(part) + 1);
                        partsTaken++;
                    }
                }

                if (partsTaken > 0) {
                    System.out.println(name + " collected " + partsTaken + " parts. Inventory: " + inventory);
                }

                Thread.sleep(50); // Pause before next day
            }

            // After 100 days, try to build final robots
            while (canBuildRobot()) {
                buildRobot();
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public int getRobotsBuilt() {
        return robotsBuilt;
    }

    public String getName() {
        return name;
    }
}