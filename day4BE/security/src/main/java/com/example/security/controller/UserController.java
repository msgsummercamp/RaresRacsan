package com.example.security.controller;

import com.example.security.dto.SuccessResponse;
import com.example.security.dto.UpdateUserRequest;
import com.example.security.dto.UserPageResponse;
import com.example.security.dto.UserRequest;
import com.example.security.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.example.security.model.User;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Validated
@Tag(name = "User Management", description = "Operations related to user management")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieve a list of all users")
    @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    public ResponseEntity<UserPageResponse> getAllUsers(@RequestParam (defaultValue = "0") int page,
                                                           @RequestParam (defaultValue = "5") int size,
                                                           @RequestParam (defaultValue = "id") String sortBy,
                                                           @RequestParam (defaultValue = "asc") String sortDirection) {
        // moved validation logic to UserService

        Page<User> userPage = userService.getAllUsers(page, size, sortBy, sortDirection);

        // created dto for page response
        UserPageResponse response = new UserPageResponse(
                userPage.getContent(),
                userPage.getNumber(),
                userPage.getTotalPages(),
                userPage.getTotalElements(),
                userPage.hasNext(),
                userPage.hasPrevious(),
                userPage.getSize()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    @Operation(summary = "Add a new user", description = "Create a new user with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "409", description = "User already exists",
                    content = @Content(schema = @Schema(implementation = Map.class)))
    })
    public ResponseEntity<SuccessResponse<User>> addUser(@Valid @RequestBody UserRequest userRequest) {
        User savedUser = userService.addUser(userRequest);
        return ResponseEntity.status(201).body(new SuccessResponse<>("User added successfully", savedUser));
    }

    @PutMapping("/update")
    @Operation(summary = "Update an existing user", description = "Update the details of an existing user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = Map.class)))
    })
    public ResponseEntity<SuccessResponse<User>> updateUser(@Valid @RequestBody UpdateUserRequest updateUserRequest) {
        User updatedUser = userService.updateUser(updateUserRequest);
        return ResponseEntity.ok(new SuccessResponse<>("User updated successfully", updatedUser));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a user", description = "Delete a user by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = Map.class)))
    })
    public ResponseEntity<SuccessResponse<Void>> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.status(204).body(new SuccessResponse<>("User deleted successfully"));
    }

    @PatchMapping("/update-email/{id}")
    @Operation(summary = "Update user email", description = "Update the email address of a user by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User email updated successfully",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = Map.class)))
    })
    public ResponseEntity<SuccessResponse<User>> updateEmailForUser(@PathVariable @Positive(message = "Id must be positive") Integer id, @RequestParam @NotBlank(message = "Email is required") @Email(message = "Email must be valid") String email) {
        User updatedUser = userService.updateEmailForUser(id, email);
        return ResponseEntity.ok(new SuccessResponse<>("User email updated successfully", updatedUser));
    }
}