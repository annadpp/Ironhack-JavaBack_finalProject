package com.ironhack.locmgmt.users;

import com.ironhack.locmgmt.model.users.User;
import com.ironhack.locmgmt.model.enums.UserType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserTest {
    private User user;

    @BeforeEach
    void setUp() {
        user = new User("testUser", "testPassword", "Test User", "test@example.com", UserType.ADMIN);
    }

    @Test
    void createUser() {
        assertNotNull(user);
        assertEquals("testUser", user.getUsername());
        assertEquals("testPassword", user.getPassword());
        assertEquals("Test User", user.getName());
        assertEquals("test@example.com", user.getEmail());
        assertEquals(UserType.ADMIN, user.getUserType());
    }

    @Test
    void testUsernameSetter() {
        user.setUsername("newUsername");
        assertEquals("newUsername", user.getUsername());
    }

    @Test
    void testPasswordSetter() {
        user.setPassword("newPassword");
        assertEquals("newPassword", user.getPassword());
    }

    @Test
    void testNameSetter() {
        user.setName("New Name");
        assertEquals("New Name", user.getName());
    }

    @Test
    void testEmailSetter() {
        user.setEmail("new@example.com");
        assertEquals("new@example.com", user.getEmail());
    }

    @Test
    void testUserTypeSetter() {
        user.setUserType(UserType.ADMIN);
        assertEquals(UserType.ADMIN, user.getUserType());
    }
}
