package org.example;

import org.example.core.ApplicationContext;
import org.example.services.UserService;
import org.example.services.IdGeneratorService;
import org.example.repositories.UserRepository;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Starting ApplicationContext ===");

        ApplicationContext context = new ApplicationContext("org.example");

        System.out.println("\n=== Testing Singleton Beans ===");

        UserService userService1 = context.getBean(UserService.class);
        UserService userService2 = context.getBean(UserService.class);

        System.out.println("UserService instances are the same: " + (userService1 == userService2));
        userService1.findUser();

        System.out.println("\n=== Testing Prototype Beans ===");

        IdGeneratorService idGenerator1 = context.getBean(IdGeneratorService.class);
        IdGeneratorService idGenerator2 = context.getBean(IdGeneratorService.class);
        IdGeneratorService idGenerator3 = context.getBean(IdGeneratorService.class);

        System.out.println("IdGenerator1 == IdGenerator2: " + (idGenerator1 == idGenerator2));
        System.out.println("IdGenerator1 == IdGenerator3: " + (idGenerator1 == idGenerator3));
        System.out.println("IdGenerator2 == IdGenerator3: " + (idGenerator2 == idGenerator3));

        System.out.println("\n=== Testing ID Generation ===");
        System.out.println("ID 1 from generator1: " + idGenerator1.generateId());
        System.out.println("ID 1 from generator3: " + idGenerator3.generateId());
        System.out.println("ID 2 from generator1: " + idGenerator1.generateId());
        System.out.println("ID 1 from generator2: " + idGenerator2.generateId());
        System.out.println("ID 2 from generator3: " + idGenerator3.generateId());
        System.out.println("ID 3 from generator1: " + idGenerator1.generateId());

        System.out.println("\n=== Generator Statistics ===");
        System.out.println("Generator1: " + idGenerator1.getStats());
        System.out.println("Generator2: " + idGenerator2.getStats());
        System.out.println("Generator3: " + idGenerator3.getStats());

        System.out.println("\n=== Testing UserRepository Directly ===");

        UserRepository userRepository1 = context.getBean(UserRepository.class);
        UserRepository userRepository2 = context.getBean(UserRepository.class);

        System.out.println("UserRepository instances are the same: " + (userRepository1 == userRepository2));
        userRepository1.save();

        System.out.println("\n=== Application Context Demo Completed ===");
    }
}