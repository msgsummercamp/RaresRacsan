package com.example.springboot.controller;

import com.example.springboot.model.User;
import com.example.springboot.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Validated
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final IUserService userService;

    public UserController(@Autowired IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers(@RequestParam(defaultValue = "1", required = false) @Min(1) Integer minId) {
        return userService.getUsers(minId);
    }
}
