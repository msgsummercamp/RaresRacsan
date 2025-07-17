package com.example.springdata.service;

import com.example.springdata.model.User;
import com.example.springdata.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final IUserRepository userRepository;

    public UserService(@Autowired IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    public void updateUserWithId(Integer id, User user) {
        if (userRepository.existsById(id)) {
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User with id " + id + " does not exist.");
        }
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public long countUsers() {
        return userRepository.countUsers();
    }
}
