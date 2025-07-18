package com.example.rest.controller;

import com.example.rest.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.rest.model.User;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllUsers(@RequestParam (defaultValue = "0") int page,
                                                           @RequestParam (defaultValue = "5") int size,
                                                           @RequestParam (defaultValue = "id") String sortBy,
                                                           @RequestParam (defaultValue = "asc") String sortDirection) {
        Map<String, Object> response = new HashMap<>();

        if (page < 0 || size <= 0) {
            response.put("message", "Invalid pagination parameters");
            response.put("status", "error");
            return ResponseEntity.status(400).body(response);
        }

        try {
            Page<User> userPage = userService.getAllUsers(page, size, sortBy, sortDirection);

            response.put("message", "Users retrieved successfully");
            response.put("status", "success");
            response.put("users", userPage.getContent());
            response.put("pagination", Map.of(
                "currentPage", userPage.getNumber(),
                "totalPages", userPage.getTotalPages(),
                "totalElements", userPage.getTotalElements(),
                "hasNext", userPage.hasNext(),
                "hasPrevious", userPage.hasPrevious(),
                "size", userPage.getSize()
            ));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Failed to retrieve users: " + e.getMessage());
            response.put("status", "error");
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addUser(@RequestParam String username, @RequestParam String email) {
        Map<String, String> response = new HashMap<>();

        if(username == null || username.isEmpty() || email == null || email.isEmpty()) {
            response.put("message", "Username and email are required");
            response.put("status", "error");
            return ResponseEntity.status(400).body(response);
        }

        try {
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            userService.addUser(user);

            response.put("message", "User added successfully");
            response.put("status", "success");
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            response.put("message", "Failed to add user: " + e.getMessage());
            response.put("status", "error");

            String errorMessage = e.getMessage().toLowerCase();
            if (errorMessage.contains("duplicate") || errorMessage.contains("constraint") ||
                    errorMessage.contains("unique")) {
                return ResponseEntity.status(409).body(response);
            }
            return ResponseEntity.status(500).body(response);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Map<String, String>> updateUser(@RequestParam Integer id, @RequestParam String username, @RequestParam String email) {
        Map<String, String> response = new HashMap<>();

        if(id == null || username == null || username.isEmpty() || email == null || email.isEmpty()) {
            response.put("message", "ID, username, and email are required");
            response.put("status", "error");
            return ResponseEntity.status(400).body(response);
        }

        try {
            userService.updateUser(id, username, email);

            response.put("message", "User updated successfully");
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Failed to update user: " + e.getMessage());
            response.put("status", "error");

            String errorMessage = e.getMessage().toLowerCase();
            if (errorMessage.contains("duplicate") || errorMessage.contains("constraint") ||
                    errorMessage.contains("unique")) {
                return ResponseEntity.status(409).body(response);
            } else if (errorMessage.contains("not found") || errorMessage.contains("no entity")) {
                return ResponseEntity.status(404).body(response);
            }
            return ResponseEntity.status(500).body(response);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Integer id) {
        Map<String, String> response = new HashMap<>();

        try {
            userService.deleteUser(id);

            response.put("message", "User deleted successfully");
            response.put("status", "success");
            return ResponseEntity.status(204).body(response);
        } catch (Exception e) {
            response.put("message", "Failed to delete user: " + e.getMessage());
            response.put("status", "error");

            String errorMessage = e.getMessage().toLowerCase();
            if (errorMessage.contains("not found") || errorMessage.contains("no entity")) {
                return ResponseEntity.status(404).body(response);
            }
            return ResponseEntity.status(500).body(response);
        }
    }

    @PatchMapping("/update-email/{id}")
    public ResponseEntity<Map<String, String>> updateEmailForUser(@PathVariable Integer id, @RequestParam String email) {
        Map<String, String> response = new HashMap<>();

        if(email == null || email.isEmpty()) {
            response.put("message", "Email is required");
            response.put("status", "error");
            return ResponseEntity.status(400).body(response);
        }

        try {
            userService.updateEmailForUser(id, email);

            response.put("message", "User email updated successfully");
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } catch(Exception e) {
            response.put("message", "Failed to update user email: " + e.getMessage());
            response.put("status", "error");

            String errorMessage = e.getMessage().toLowerCase();
            if (errorMessage.contains("duplicate") || errorMessage.contains("constraint") ||
                    errorMessage.contains("unique")) {
                return ResponseEntity.status(409).body(response);
            } else if (errorMessage.contains("not found") || errorMessage.contains("no entity")) {
                return ResponseEntity.status(404).body(response);
            }
            return ResponseEntity.status(500).body(response);
        }
    }
}