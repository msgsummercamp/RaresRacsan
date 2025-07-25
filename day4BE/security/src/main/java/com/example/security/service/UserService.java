package com.example.security.service;

import com.example.security.dto.UpdateUserRequest;
import com.example.security.dto.UserRequest;
import com.example.security.exception.DuplicateUserException;
import com.example.security.exception.UserNotFoundException;
import com.example.security.model.User;
import com.example.security.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<User> getAllUsers(int page, int size, String sortBy, String sortDir) {
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Page number must be non-negative and size must be positive");
        }

        Sort sort = sortDir.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return userRepository.findAll(pageable);
    }
    public User addUser(UserRequest userRequest){
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new DuplicateUserException("User with username '" + userRequest.getUsername() + "' already exists");
        }

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new DuplicateUserException("User with email '" + userRequest.getEmail() + "' already exists");
        }

        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());

        return userRepository.save(user);
    }

    public User updateUser(UpdateUserRequest updateUserRequest) {
        User user = userRepository.findById(updateUserRequest.getId())
                .orElseThrow(() -> new UserNotFoundException("User with ID " + updateUserRequest.getId() + " not found"));

        // Check for username conflicts (exclude current user)
        if (!user.getUsername().equals(updateUserRequest.getUsername())
                && userRepository.existsByUsername(updateUserRequest.getUsername())) {
            throw new DuplicateUserException("Username '" + updateUserRequest.getUsername() + "' already exists");
        }

        // Check for email conflicts (exclude current user)
        if (!user.getEmail().equals(updateUserRequest.getEmail())
                && userRepository.existsByEmail(updateUserRequest.getEmail())) {
            throw new DuplicateUserException("Email '" + updateUserRequest.getEmail() + "' already exists");
        }

        user.setUsername(updateUserRequest.getUsername());
        user.setEmail(updateUserRequest.getEmail());
        return userRepository.save(user);
    }

    public User updateEmailForUser(Integer id, String email) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));

        if (!user.getEmail().equals(email) && userRepository.existsByEmail(email)) {
            throw new DuplicateUserException("Email '" + email + "' already exists");
        }

        user.setEmail(email);
        return userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User with ID " + id + " not found");
        }
        userRepository.deleteById(id);
    }}