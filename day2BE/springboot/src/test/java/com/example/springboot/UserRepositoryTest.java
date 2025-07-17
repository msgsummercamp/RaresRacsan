package com.example.springboot;

import com.example.springboot.model.User;
import com.example.springboot.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {
    @Test
    void testGetUsers() {
        UserRepository userRepository = new UserRepository();
        List<User> users = userRepository.getUsers();

        // Check if the size of the list is as expected
        assertEquals(4, users.size(), "The user list should contain 4 users");

        // Check if the first user's name is as expected
        assertEquals("John Doe", users.getFirst().getName(), "The first user's name should be 'John Doe'");
    }
}
