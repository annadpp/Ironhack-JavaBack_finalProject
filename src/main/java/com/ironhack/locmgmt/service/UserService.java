package com.ironhack.locmgmt.service;

import com.ironhack.locmgmt.exception.EmptyListException;
import com.ironhack.locmgmt.model.enums.UserType;
import com.ironhack.locmgmt.model.users.User;
import com.ironhack.locmgmt.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.dao.*;
/*
import org.springframework.security.crypto.password.PasswordEncoder;
*/
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;
/*
    private final PasswordEncoder passwordEncoder;
*/

    public List<User> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            if (users.isEmpty()) {
                throw new EmptyListException("No users were found");
            }
            return users;
        } catch (DataAccessException e) {
            throw new DataRetrievalFailureException("Error while retrieving all users", e);
        }
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    public void deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Rate not found with id: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Error deleting rate with id: " + id);
        }
    }

    //Update password
    /*public void updatePassword(String username, String oldPassword, String newPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("Incorrect previous password");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }*/
}
