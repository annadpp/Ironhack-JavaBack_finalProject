package com.ironhack.locmgmt.service;

import com.ironhack.locmgmt.model.enums.ProjectType;
import com.ironhack.locmgmt.model.enums.Role;
import com.ironhack.locmgmt.model.users.Admin;
import com.ironhack.locmgmt.model.users.Linguist;
import com.ironhack.locmgmt.model.users.ProjectManager;
import com.ironhack.locmgmt.util.SecurityUtil;
import com.ironhack.locmgmt.security.AuthenticationResponse;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private final UserRepository repository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private SecurityUtil securityUtil;

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

    public AuthenticationResponse registerUser(User request, Role role) {
        if (repository.findByUsername(request.getUsername()).isPresent()) {
            return new AuthenticationResponse(null, role.name() + " already exists");
        }

        User user = null;
        if (request instanceof Admin) {
            Admin adminRequest = (Admin) request;
            Admin admin = new Admin();
            admin.setDepartment(adminRequest.getDepartment());
            user = admin;
        } else if (request instanceof Linguist) {
            Linguist linguistRequest = (Linguist) request;
            Linguist linguist = new Linguist();
            linguist.setSourceLanguages(linguistRequest.getSourceLanguages());
            linguist.setTargetLanguages(linguistRequest.getTargetLanguages());
            linguist.setProjectTypes(linguistRequest.getProjectTypes());
            linguist.setDtpTechnologies(linguistRequest.getDtpTechnologies());
            linguist.setLinguisticTechnologies(linguistRequest.getLinguisticTechnologies());
            user = linguist;
        } else if (request instanceof ProjectManager) {
            ProjectManager pmRequest = (ProjectManager) request;
            ProjectManager projectManager = new ProjectManager();
            List<ProjectType> projectTypes = pmRequest.getProjectTypes();
            for (ProjectType projectType : projectTypes) {
                if (projectType != ProjectType.LINGUISTIC && projectType != ProjectType.DTP) {
                    return new AuthenticationResponse(null, "Invalid project type. Project type can only be LINGUISTIC or DTP");
                }
            }
            projectManager.setSpokenLanguages(pmRequest.getSpokenLanguages());
            projectManager.setProjectTypes(pmRequest.getProjectTypes());
            user = projectManager;
        } else {
            return new AuthenticationResponse(null, "Invalid user type for role " + role);
        }

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);

        user = repository.save(user);

        String jwt = jwtService.generateToken(user);

/*
        saveUserToken(jwt, user);
*/

        return new AuthenticationResponse(jwt, role.name() + " registration was successful");
    }

    public AuthenticationResponse registerAdmin(Admin request) {
        return registerUser(request, Role.ADMIN);
    }

    public AuthenticationResponse registerLinguist(Linguist request) {
        return registerUser(request, Role.LINGUIST);
    }

    public AuthenticationResponse registerProjectManager(ProjectManager request) {
        return registerUser(request, Role.PROJECT_MANAGER);
    }

    //For Login
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

        return new AuthenticationResponse(jwt, "Login was successful");

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

    public void updateUserPassword(String username, String newPassword) throws AccessDeniedException {
        //Get user based in username
        Optional<User> optionalUser = repository.findByUsername(username);

        //Verify if user exists
        if (optionalUser.isEmpty()) {
            throw new AccessDeniedException("User not found");
        }

        //Update user password
        User currentUser = optionalUser.get();
        currentUser.setPassword(passwordEncoder.encode(newPassword));
        repository.save(currentUser);
    }
}
