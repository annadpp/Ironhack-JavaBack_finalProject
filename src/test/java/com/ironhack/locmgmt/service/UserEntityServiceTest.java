package com.ironhack.locmgmt.service;

import com.ironhack.locmgmt.exception.EmptyListException;
import com.ironhack.locmgmt.model.enums.UserType;
import com.ironhack.locmgmt.model.users.UserEntity;
import com.ironhack.locmgmt.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserEntityServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void deleteUser_ExistingId_DeletedSuccessfully() {
        userRepository.deleteAll(); // Clear the repository
        UserEntity userEntity = new UserEntity("user1", "password1", "User 1", "user1@example.com", UserType.ADMIN);
        userRepository.save(userEntity);

        userService.deleteUser(userEntity.getId());

        assertThrows(EntityNotFoundException.class, () -> userService.getUserById(userEntity.getId()));
    }

    @Test
    void getAllUsers_Success() {
        UserEntity userEntity1 = new UserEntity("user1", "password1", "User 1", "user1@example.com", UserType.PROJECT_MANAGER);
        UserEntity userEntity2 = new UserEntity("user2", "password2", "User 2", "user2@example.com", UserType.ADMIN);
        userRepository.save(userEntity1);
        userRepository.save(userEntity2);

        List<UserEntity> result = userService.getAllUsers();

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
        UserEntity userEntity = new UserEntity("user1", "password1", "User 1", "user1@example.com", UserType.PROJECT_MANAGER);
        userRepository.save(userEntity);

        UserEntity result = userService.getUserById(userEntity.getId());

        assertNotNull(result);
        assertEquals(userEntity.getUsername(), result.getUsername());
    }

    @Test
    void getUserById_NonExistingId_ExceptionThrown() {
        assertThrows(EntityNotFoundException.class, () -> userService.getUserById(1L));
    }
}
