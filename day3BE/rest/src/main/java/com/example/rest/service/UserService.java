package com.example.rest.service;

import com.example.rest.exception.DuplicateUserException;
import com.example.rest.exception.UserNotFoundException;
import com.example.rest.model.User;
import com.example.rest.repository.IUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<User> getAllUsers(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return userRepository.findAll(pageable);
    }
    public User addUser(User user){
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new DuplicateUserException("User with username '" + user.getUsername() + "' already exists");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateUserException("User with email '" + user.getEmail() + "' already exists");
        }

        return userRepository.save(user);
    }

    public User updateUser(Integer id, String username, String email) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));

        user.setUsername(username);
        user.setEmail(email);
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
