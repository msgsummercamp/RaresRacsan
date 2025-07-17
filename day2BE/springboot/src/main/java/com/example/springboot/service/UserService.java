package com.example.springboot.service;

import com.example.springboot.model.User;
import com.example.springboot.repository.IUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(@Autowired IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUsers(Integer minId) {
        logger.info("Fetching all users from the repository with minId: {}", minId);
        return userRepository.getUsers().stream()
                .filter(user -> user.getId() >= minId)
                .toList();
    }

    @Override
    public List<User> getUsers() {
        logger.info("Fetching all users from the repository");
        return userRepository.getUsers();
    }
}
