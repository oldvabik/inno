package org.example.repositories;

import org.example.annotations.Component;
import org.example.annotations.Scope;

@Component
@Scope
public class UserRepository {
    public void save() {
        System.out.println("User saved!");
    }
}

