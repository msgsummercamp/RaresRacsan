package com.example.springboot.repository;

import com.example.springboot.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository implements IUserRepository{
    private final List<User> users = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    public UserRepository() {
        // Initializing with some dummy data
        users.add(new User(1, "John Doe", "mail4@gmail.com"));
        users.add(new User(2, "John Doe2", "mail1@gmail.com"));
        users.add(new User(3, "John Doe3", "mail2@gmail.com"));
        users.add(new User(4, "John Doe4", "mail3@gmail.com"));
        logger.info("UserRepository initialized with {} users", users.size());
    }

    @Override
    public List<User> getUsers() {
        List<String> userNames = users.stream()
                .map(User::getName)
                .toList();
        logger.info("Fetching all user names: {}", userNames);
        return users;
    }
}
