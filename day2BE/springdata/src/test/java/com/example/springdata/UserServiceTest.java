package com.example.springdata;

import com.example.springdata.model.User;
import com.example.springdata.repository.IUserRepository;
import com.example.springdata.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void findByUsername_WhenUserExists_ShouldReturnUser() {
        String username = "testUser";
        User user = new User();
        user.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        User result = userService.findByUsername(username);

        assertEquals(username, result.getUsername());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void findByUsername_WhenUserNotFound_ShouldThrowException() {
        String username = "nonExistentUser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.findByUsername(username));
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void findByEmail_WhenUserExists_ShouldReturnUser() {
        String email = "email@gmail.com";
        User user = new User();
        user.setEmail(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        User result = userService.findByEmail(email);

        assertEquals(email, result.getEmail());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void findByEmail_WhenUserNotFound_ShouldThrowException() {
        String email = "nonexistent@gmail.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.findByEmail(email));
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void addUser_WhenValidUser_ShouldSaveUser() {
        User user = new User();
        user.setUsername("newUser");

        userService.addUser(user);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void deleteUser_WhenUserExists_ShouldDeleteUser() {
        Integer userId = 1;
        when(userRepository.existsById(userId)).thenReturn(true);

        userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void updateUserWithId_WhenUserExists_ShouldUpdateUser() {
        Integer userId = 1;
        User user = new User();
        user.setUsername("updatedUser");
        when(userRepository.existsById(userId)).thenReturn(true);

        userService.updateUserWithId(userId, user);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void findAll_ShouldReturnAllUsers() {
        userService.findAll();

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void countAllUsers_ShouldReturnUserCount() {
        long expectedCount = 5L;
        when(userRepository.countAllUsers()).thenReturn(expectedCount);

        long actualCount = userService.countAllUsers();

        assertEquals(expectedCount, actualCount);
        verify(userRepository, times(1)).countAllUsers();
    }
}