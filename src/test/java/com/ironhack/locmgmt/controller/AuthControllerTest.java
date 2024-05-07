package com.ironhack.locmgmt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.locmgmt.model.enums.Languages;
import com.ironhack.locmgmt.model.enums.Role;
import com.ironhack.locmgmt.model.enums.Department;
import com.ironhack.locmgmt.model.users.Admin;
import com.ironhack.locmgmt.model.users.Linguist;
import com.ironhack.locmgmt.model.users.ProjectManager;

import com.ironhack.locmgmt.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthenticationService authService;

    private Admin sampleAdmin;
    private Linguist sampleLinguist;
    private ProjectManager sampleProjectManager;

    @BeforeEach
    void setUp() {
        sampleAdmin = new Admin("adminUsername", "adminPassword", "John Doe", "admin@example.com", Role.ADMIN, Department.IT);
        sampleLinguist = new Linguist("linguistUsername", "linguistPassword", "Jane Smith", "linguist@example.com", Role.LINGUIST,null,
                null, null,
                null, null);
        sampleProjectManager = new ProjectManager("pmUsername", "pmPassword", "Michael Johnson", "manager@example.com", Role.PROJECT_MANAGER,
                Arrays.asList(Languages.ENGLISH, Languages.GERMAN), null);
    }

    @Test
    void testRegisterAdmin() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String adminJson = objectMapper.writeValueAsString(sampleAdmin);
        mockMvc.perform(post("/auth/register/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(adminJson))
                .andExpect(status().isCreated());
    }

    @Test
    void testRegisterLinguist() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String linguistJson = objectMapper.writeValueAsString(sampleLinguist);
        mockMvc.perform(post("/auth/register/linguist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(linguistJson))
                .andExpect(status().isCreated());
    }

    @Test
    void testRegisterProjectManager() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String projectManagerJson = objectMapper.writeValueAsString(sampleProjectManager);
        mockMvc.perform(post("/auth/register/project-manager")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(projectManagerJson))
                .andExpect(status().isCreated());
    }
}