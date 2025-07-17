package com.example.springboot.controller;

import com.example.springboot.model.User;
import com.example.springboot.service.IUserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
public class UserController {
    private final IUserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    public UserController(@Autowired IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getUsers(@RequestParam(defaultValue = "1", required = false) @Min(1) Integer minId) {
        logger.info("Fetching all users with minId");
        return userService.getUsers(minId);
    }
}
