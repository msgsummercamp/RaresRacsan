package com.example.springboot.service;

import com.example.springboot.model.User;
import com.example.springboot.repository.IUserRepository;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

@Slf4j
@Service
public class UserService implements IUserService {
    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUsers(Integer minId) {
        log.info("Fetching all users from the repository with minId: {}", minId);
        return userRepository.getUsers().stream()
                .filter(user -> user.getId() >= minId)
                .toList();
    }
}
