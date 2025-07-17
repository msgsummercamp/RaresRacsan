package com.example.springdata;

import com.example.springdata.model.User;
import com.example.springdata.repository.IUserRepository;
import com.example.springdata.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testFindByUsername() {
        String username = "testUser";
        User user = new User();
        user.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByUsername(username);

        assertTrue(result.isPresent());
        assertEquals(username, result.get().getUsername());
        verify(userRepository, times(1)).findByUsername(username);

    }

    @Test
    void testFindByEmail() {
        String email = "email@gmail.com";
        User user = new User();
        user.setEmail(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        Optional<User> result = userService.findByEmail(email);
        assertTrue(result.isPresent());
        assertEquals(email, result.get().getEmail());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void testAddUser() {
        User user = new User();
        user.setUsername("newUser");
        userService.addUser(user);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testDeleteUser() {
        Integer userId = 1;
        userService.deleteUser(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testUpdateUserWithId() {
        Integer userId = 1;
        User user = new User();
        user.setUsername("updatedUser");

        when(userRepository.existsById(userId)).thenReturn(true);

        userService.updateUserWithId(userId, user);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testFindAll() {
        userService.findAll();
        verify(userRepository, times(1)).findAll();
    }
}
