package com.ironhack.locmgmt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.locmgmt.model.users.User;
import com.ironhack.locmgmt.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    /*DOESN'T WORK WITH SECURITY ENABLED*/
    @Test
    void getAllUsers_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/get"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getUserById_Success() throws Exception {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/users/save")
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Long userId = Long.valueOf(responseBody.split(":")[1].split(",")[0]);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/get/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }

    @Test
    void deleteUser_Success() throws Exception {
        User user = new User();
        user.setName("Jane Smith");
        user.setEmail("jane.smith@example.com");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/users/save")
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Long userId = Long.valueOf(responseBody.split(":")[1].split(",")[0]);

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/delete/{id}", userId))
                .andExpect(status().isNoContent());
    }

    private String asJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
