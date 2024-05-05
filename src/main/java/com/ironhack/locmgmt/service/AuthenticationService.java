package com.ironhack.locmgmt.service;

import com.ironhack.locmgmt.security.AuthenticationResponse;
import com.ironhack.locmgmt.model.enums.Role;
/*
import com.ironhack.locmgmt.security.Token;
*/
import com.ironhack.locmgmt.model.users.User;
/*
import com.ironhack.locmgmt.repository.TokenRepository;
*/
import com.ironhack.locmgmt.repository.UserRepository;
/*
import com.ironhack.locmgmt.security.Token;
*/
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

   /* private final TokenRepository tokenRepository;*/

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository repository,
                                 PasswordEncoder passwordEncoder,
                                 JwtService jwtService,
                                 /*TokenRepository tokenRepository,*/
                                 AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
/*
        this.tokenRepository = tokenRepository;
*/
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(User request) {

        // check if user already exist. if exist than authenticate the user
        if(repository.findByUsername(request.getUsername()).isPresent()) {
            return new AuthenticationResponse(null, "User already exist");
        }

        User user = new User();
/*
        user.setName(request.getName());
*/
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));


        user.setRole(request.getRole());

        user = repository.save(user);

        String jwt = jwtService.generateToken(user);

/*
        saveUserToken(jwt, user);
*/

        return new AuthenticationResponse(jwt, "User registration was successful");

    }

    public AuthenticationResponse authenticate(User request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = repository.findByUsername(request.getUsername()).orElseThrow();
        String jwt = jwtService.generateToken(user);

        /*revokeAllTokenByUser(user);
        saveUserToken(jwt, user);*/

        return new AuthenticationResponse(jwt, "User login was successful");

    }
    /*private void revokeAllTokenByUser(User user) {
        List<Token> validTokens = tokenRepository.findAllTokensByUser(user.getId());
        if(validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(t-> {
            t.setLoggedOut(true);
        });

        tokenRepository.saveAll(validTokens);
    }*/
    /*private void saveUserToken(String jwt, User user) {
        Token token = new Token();
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }*/
}
