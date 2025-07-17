package com.example.springboot;

import com.example.springboot.model.User;
import com.example.springboot.repository.UserRepository;
import com.example.springboot.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock UserRepository userRepository;
    @InjectMocks UserService userService;

    @Test
    void testGetUsers() {
        List<User> users = List.of(new User(1, "John Doe", "mail4@gmail.com"));
        when(userRepository.getUsers()).thenReturn(users);
        List<User> result = userService.getUsers();
        assertEquals(users, result, "The returned user list should match the mocked data");
    }
}
