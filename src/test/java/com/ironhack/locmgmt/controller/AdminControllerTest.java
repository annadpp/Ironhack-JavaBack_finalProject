package com.ironhack.locmgmt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.locmgmt.model.enums.Role;
import com.ironhack.locmgmt.model.users.Admin;
import com.ironhack.locmgmt.service.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*DOESN'T WORK WITH SECURITY ENABLED*/
@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthenticationService authenticationService;

    @Test
    void createAdmin_Success() throws Exception {
        Admin admin = new Admin();
        admin.setName("John Doe");
        admin.setEmail("john.doe@example.com");
        admin.setUsername("johndoe");
        admin.setPassword("password");
        admin.setRole(Role.ADMIN);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/admins/create")
                        .content(asJsonString(admin))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        assert (responseBody.contains("John Doe"));
    }

    @Test
    void getAdminById_Success() throws Exception {
        //Create an admin
        Admin admin = new Admin();
        admin.setName("Jane Doe");
        admin.setEmail("jane.doe@example.com");
        admin.setUsername("janedoe");
        admin.setPassword("password");
        admin.setRole(Role.ADMIN);
        //Save the admin
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/admins/create")
                        .content(asJsonString(admin))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        //Extract admin id from the response
        String responseBody = result.getResponse().getContentAsString();
        Long adminId = Long.valueOf(responseBody.split(":")[1].split(",")[0]);

        //Perform GET request to fetch the admin by id
        mockMvc.perform(MockMvcRequestBuilders.get("/admins/get/{id}", adminId))
                .andExpect(status().isOk()) // Expect HTTP status 200
                .andExpect(jsonPath("$.name").value(admin.getName())) // Expect the correct name in the response
                .andExpect(jsonPath("$.email").value(admin.getEmail())); // Expect the correct email in the response
    }

    @Test
    void getAllAdmins_Success() throws Exception {
        //Perform GET request to fetch all admins
        mockMvc.perform(MockMvcRequestBuilders.get("/admins/get"))
                .andExpect(status().isOk()) // Expect HTTP status 200
                .andExpect(jsonPath("$").isArray()); // Expect response to be an array
    }

    //Helper method to convert object to JSON string
    private String asJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
