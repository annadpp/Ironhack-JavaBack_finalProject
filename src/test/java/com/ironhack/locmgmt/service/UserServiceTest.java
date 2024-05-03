package com.ironhack.locmgmt.service;

import com.ironhack.locmgmt.exception.EmptyListException;
import com.ironhack.locmgmt.model.enums.UserType;
import com.ironhack.locmgmt.model.users.User;
import com.ironhack.locmgmt.repository.UserRepository;
import com.ironhack.locmgmt.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void deleteUser_ExistingId_DeletedSuccessfully() {
        userRepository.deleteAll(); // Clear the repository
        User user = new User("user1", "password1", "User 1", "user1@example.com", UserType.ADMIN);
        userRepository.save(user);

        userService.deleteUser(user.getId());

        assertThrows(EntityNotFoundException.class, () -> userService.getUserById(user.getId()));
    }

    @Test
    void getAllUsers_Success() {
        User user1 = new User("user1", "password1", "User 1", "user1@example.com", UserType.PROJECT_MANAGER);
        User user2 = new User("user2", "password2", "User 2", "user2@example.com", UserType.ADMIN);
        userRepository.save(user1);
        userRepository.save(user2);

        List<User> result = userService.getAllUsers();

        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
    }

    @Test
    void getAllUsers_EmptyList_ExceptionThrown() {
        EmptyListException exception = assertThrows(EmptyListException.class, () -> userService.getAllUsers());
        assertEquals("No users were found", exception.getMessage());
    }

    @Test
    void getUserById_ExistingId_UserReturned() {
        User user = new User("user1", "password1", "User 1", "user1@example.com", UserType.PROJECT_MANAGER);
        userRepository.save(user);

        User result = userService.getUserById(user.getId());

        assertNotNull(result);
        assertEquals(user.getUsername(), result.getUsername());
    }

    @Test
    void getUserById_NonExistingId_ExceptionThrown() {
        assertThrows(EntityNotFoundException.class, () -> userService.getUserById(1L));
    }
}
