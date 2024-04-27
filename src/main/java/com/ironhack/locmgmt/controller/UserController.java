package com.ironhack.locmgmt.controller;

import com.ironhack.locmgmt.model.enums.UserType;
import com.ironhack.locmgmt.model.users.User;
import com.ironhack.locmgmt.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/usersByUserType/{userType}")
    public ResponseEntity<List<User>> getUsersByUserType(@PathVariable UserType userType) {
        List<User> users = userService.getUsersByUserType(userType);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
