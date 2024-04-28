package com.ironhack.locmgmt.service;

import com.ironhack.locmgmt.model.enums.UserType;
import com.ironhack.locmgmt.model.users.ProjectManager;
import com.ironhack.locmgmt.model.users.User;
import com.ironhack.locmgmt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    //Filters by user type
    public List<User> getUsersByUserType(UserType userType) {
        return userRepository.findByUserType(userType);
    }
}
