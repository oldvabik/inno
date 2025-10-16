package org.example;

import org.example.threads.Faction;
import org.example.threads.Factory;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Skynet simulation starting for 100 days...\n");

        Factory factory = new Factory();
        Faction world = new Faction("World", factory);
        Faction wednesday = new Faction("Wednesday", factory);

        factory.start();
        Thread.sleep(10);
        world.start();
        wednesday.start();

        factory.join();

        Thread.sleep(500);
        world.interrupt();
        wednesday.interrupt();
        world.join();
        wednesday.join();

        System.out.println("\n=== FINAL RESULTS ===");
        world.printStatus();
        wednesday.printStatus();

        System.out.println();
        int worldRobots = world.calculateCompleteRobots();
        int wednesdayRobots = wednesday.calculateCompleteRobots();

        if (worldRobots > wednesdayRobots) {
            System.out.println("Winner: World!");
        } else if (wednesdayRobots > worldRobots) {
            System.out.println("Winner: Wednesday!");
        } else {
            System.out.println("It's a tie!");
        }
    }
}