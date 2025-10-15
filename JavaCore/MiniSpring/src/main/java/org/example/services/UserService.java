package org.example.services;

import org.example.annotations.Autowired;
import org.example.annotations.Component;
import org.example.core.InitializingBean;
import org.example.repositories.UserRepository;

@Component
public class UserService implements InitializingBean {

    @Autowired
    private UserRepository userRepository;

    public void findUser() {
        System.out.println("Finding user...");
        userRepository.save();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("UserService initialized!");
    }

}
