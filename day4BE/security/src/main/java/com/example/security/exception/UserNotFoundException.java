package com.example.security.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Integer id) {
        super("User with ID " + id + " not found");
    }
}