package com.example.security.exception;

public class DuplicateUserException extends RuntimeException {
    public DuplicateUserException(String message) {
        super(message);
    }

    public DuplicateUserException(String field, String value) {
        super("User with " + field + " '" + value + "' already exists");
    }
}