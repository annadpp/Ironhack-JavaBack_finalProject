package com.ironhack.locmgmt.controller;

import com.ironhack.locmgmt.model.users.UserEntity;
import com.ironhack.locmgmt.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/get/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserEntity getUserById(@PathVariable Long id) {
        UserEntity userEntity = userService.getUserById(id);
        if (userEntity == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return userEntity;
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    //User only
    /*@PutMapping("/update/password/{username}")
    public ResponseEntity<String> updatePassword(@PathVariable String username,
                                                 @RequestParam String oldPassword,
                                                 @RequestParam String newPassword) {
        userService.updatePassword(username, oldPassword, newPassword);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }*/
}
