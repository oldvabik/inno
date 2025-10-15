package org.example;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== SKYNET SIMULATION ===");
        System.out.println("Starting 100-day simulation...\n");

        Factory factory = new Factory();

        Faction world = new Faction("World", factory.getStorage());
        Faction wednesday = new Faction("Wednesday", factory.getStorage());

        Thread factoryThread = new Thread(factory);
        Thread worldThread = new Thread(world);
        Thread wednesdayThread = new Thread(wednesday);

        factoryThread.start();
        worldThread.start();
        wednesdayThread.start();

        try {
            factoryThread.join();
            worldThread.join();
            wednesdayThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n=== FINAL RESULTS ===");
        System.out.println("World built robots: " + world.getRobotsBuilt());
        System.out.println("Wednesday built robots: " + wednesday.getRobotsBuilt());

        if (world.getRobotsBuilt() > wednesday.getRobotsBuilt()) {
            System.out.println("WINNER: World!");
        } else if (wednesday.getRobotsBuilt() > world.getRobotsBuilt()) {
            System.out.println("WINNER: Wednesday!");
        } else {
            System.out.println("DRAW!");
        }
    }
}