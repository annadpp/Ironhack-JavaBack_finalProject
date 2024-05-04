package com.ironhack.locmgmt.repository;

import com.ironhack.locmgmt.model.enums.UserType;
import com.ironhack.locmgmt.model.users.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class UserEntityRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveUser() {
        UserEntity userEntity = new UserEntity("username", "password", "John Doe", "john@example.com", UserType.LINGUIST);
        userRepository.save(userEntity);

        UserEntity savedUserEntity = userRepository.findById(userEntity.getId()).orElse(null);

        assertNotNull(savedUserEntity);
        assertEquals("username", savedUserEntity.getUsername());
    }

    @Test
    void testFindAllUsers() {
        UserEntity userEntity1 = new UserEntity("user1", "password1", "User One", "user1@example.com", UserType.LINGUIST);
        UserEntity userEntity2 = new UserEntity("user2", "password2", "User Two", "user2@example.com", UserType.ADMIN);
        userRepository.saveAll(List.of(userEntity1, userEntity2));

        List<UserEntity> userEntityList = userRepository.findAll();

        assertNotNull(userEntityList);
        assertEquals(2, userEntityList.size());
    }
}

