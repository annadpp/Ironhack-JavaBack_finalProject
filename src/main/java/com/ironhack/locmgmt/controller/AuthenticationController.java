package com.ironhack.locmgmt.controller;

import com.ironhack.locmgmt.model.users.Admin;
import com.ironhack.locmgmt.model.users.Linguist;
import com.ironhack.locmgmt.model.users.ProjectManager;
import com.ironhack.locmgmt.security.AuthenticationResponse;
import com.ironhack.locmgmt.model.users.User;
import com.ironhack.locmgmt.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/register/admin")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthenticationResponse registerAdmin(@RequestBody Admin request) {
        return authService.registerAdmin(request);
    }

    @PostMapping("/register/linguist")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthenticationResponse registerLinguist(@RequestBody Linguist request) {
        return authService.registerLinguist(request);
    }

    @PostMapping("/register/project-manager")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthenticationResponse registerProjectManager(@RequestBody ProjectManager request) {
        return authService.registerProjectManager(request);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse login(@RequestBody User request) {
        return authService.authenticate(request);
    }
}
