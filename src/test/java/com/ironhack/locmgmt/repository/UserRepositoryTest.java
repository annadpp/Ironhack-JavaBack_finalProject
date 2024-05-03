package com.ironhack.locmgmt.repository;

import com.ironhack.locmgmt.model.enums.UserType;
import com.ironhack.locmgmt.model.users.User;
import com.ironhack.locmgmt.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveUser() {
        User user = new User("username", "password", "John Doe", "john@example.com", UserType.LINGUIST);
        userRepository.save(user);

        User savedUser = userRepository.findById(user.getId()).orElse(null);

        assertNotNull(savedUser);
        assertEquals("username", savedUser.getUsername());
    }

    @Test
    void testFindAllUsers() {
        User user1 = new User("user1", "password1", "User One", "user1@example.com", UserType.LINGUIST);
        User user2 = new User("user2", "password2", "User Two", "user2@example.com", UserType.ADMIN);
        userRepository.saveAll(List.of(user1, user2));

        List<User> userList = userRepository.findAll();

        assertNotNull(userList);
        assertEquals(2, userList.size());
    }
}

