package com.example.rest.controller;

import com.example.rest.service.UserService;
import org.springframework.web.bind.annotation.*;
import com.example.rest.model.User;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/add")
    public void addUser(@RequestParam String username, @RequestParam String email) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        userService.addUser(user);
    }

    @PutMapping("/update")
    public void updateUser(@RequestParam Integer id, @RequestParam String username, @RequestParam String email) {
        userService.updateUser(id, username, email);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
    }

    @PatchMapping("/update-email/{id}")
    public void updateEmailForUser(@PathVariable Integer id, @RequestParam String email) {
        userService.updateEmailForUser(id, email);
    }
}
