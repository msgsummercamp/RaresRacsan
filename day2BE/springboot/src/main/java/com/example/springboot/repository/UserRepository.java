package com.example.springboot.repository;

import com.example.springboot.model.User;
import org.springframework.stereotype.Repository;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class UserRepository implements IUserRepository{
    private final List<User> users = new ArrayList<>();

    public UserRepository() {
        // Initializing with some dummy data
        users.add(new User(1, "John Doe", "mail4@gmail.com"));
        users.add(new User(2, "John Doe2", "mail1@gmail.com"));
        users.add(new User(3, "John Doe3", "mail2@gmail.com"));
        users.add(new User(4, "John Doe4", "mail3@gmail.com"));
        log.info("UserRepository initialized with {} users", users.size());
    }

    @Override
    public List<User> getUsers() {
        return users;
    }
}
