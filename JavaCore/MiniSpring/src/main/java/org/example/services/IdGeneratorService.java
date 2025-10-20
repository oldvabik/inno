package org.example.services;

import org.example.annotations.Component;
import org.example.annotations.Scope;

@Component
@Scope("prototype")
public class IdGeneratorService {
    private final String generatorId;
    private int counter = 0;

    public IdGeneratorService() {
        this.generatorId = "GEN-" + System.nanoTime() + "-" +
                Thread.currentThread().getId();
        System.out.println("IdGeneratorService created: " + generatorId);
    }

    public String generateId() {
        counter++;
        return generatorId + "-" + counter;
    }

    public String getStats() {
        return "Generator: " + generatorId + ", IDs generated: " + counter;
    }
}
