package com.example.rest.dto;

import jakarta.validation.constraints.*;

public class UpdateUserRequest {
    @NotNull(message = "ID is required")
    @Positive(message = "ID must be positive")
    private Integer id;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username can only contain letters, numbers, and underscores")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;

    public UpdateUserRequest() {}

    public UpdateUserRequest(Integer id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public @NotNull(message = "ID is required") @Positive(message = "ID must be positive") Integer getId() {
        return id;
    }

    public @NotBlank(message = "Username is required") @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters") @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username can only contain letters, numbers, and underscores") String getUsername() {
        return username;
    }

    public @NotBlank(message = "Email is required") @Email(message = "Email must be valid") @Size(max = 100, message = "Email must not exceed 100 characters") String getEmail() {
        return email;
    }

    public void setId(@NotNull(message = "ID is required") @Positive(message = "ID must be positive") Integer id) {
        this.id = id;
    }

    public void setUsername(@NotBlank(message = "Username is required") @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters") @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username can only contain letters, numbers, and underscores") String username) {
        this.username = username;
    }

    public void setEmail(@NotBlank(message = "Email is required") @Email(message = "Email must be valid") @Size(max = 100, message = "Email must not exceed 100 characters") String email) {
        this.email = email;
    }
}
