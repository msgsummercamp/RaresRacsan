package com.example.springboot.controller;

import com.example.springboot.model.User;
import com.example.springboot.service.IUserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private final IUserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    public UserController(@Autowired IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        logger.info("Fetching all users");
        return userService.getUsers();
    }
}
