package com.ironhack.locmgmt.model.users;

import com.ironhack.locmgmt.model.enums.UserType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserEntityTest {
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        userEntity = new UserEntity("testUser", "testPassword", "Test User", "test@example.com", UserType.ADMIN);
    }

    @Test
    void createUser() {
        assertNotNull(userEntity);
        assertEquals("testUser", userEntity.getUsername());
        assertEquals("testPassword", userEntity.getPassword());
        assertEquals("Test User", userEntity.getName());
        assertEquals("test@example.com", userEntity.getEmail());
        assertEquals(UserType.ADMIN, userEntity.getUserType());
    }

    @Test
    void testUsernameSetter() {
        userEntity.setUsername("newUsername");
        assertEquals("newUsername", userEntity.getUsername());
    }

    @Test
    void testPasswordSetter() {
        userEntity.setPassword("newPassword");
        assertEquals("newPassword", userEntity.getPassword());
    }

    @Test
    void testNameSetter() {
        userEntity.setName("New Name");
        assertEquals("New Name", userEntity.getName());
    }

    @Test
    void testEmailSetter() {
        userEntity.setEmail("new@example.com");
        assertEquals("new@example.com", userEntity.getEmail());
    }

    @Test
    void testUserTypeSetter() {
        userEntity.setUserType(UserType.ADMIN);
        assertEquals(UserType.ADMIN, userEntity.getUserType());
    }
}
