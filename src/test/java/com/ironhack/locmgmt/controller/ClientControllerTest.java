package com.ironhack.locmgmt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.locmgmt.controller.ClientController;
import com.ironhack.locmgmt.model.Client;
import com.ironhack.locmgmt.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Client client;

    @BeforeEach
    public void setUp() {
        client = new Client("Test Client", "test@example.com", "123456789", "Test Address");
    }

    @Test
    public void getAllClients_Valid_ReturnsListOfClients() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/clients/get"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Test Client"))
                .andExpect(jsonPath("$[0].email").value("test@example.com"));
    }

    @Test
    public void createClient_ValidClient_ReturnsCreatedClient() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/clients/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Test Client"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    public void updateClient_ExistingIdAndValidClient_ReturnsUpdatedClient() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/clients/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Test Client"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    public void getClientById_ExistingId_ReturnsClient() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/clients/get/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Test Client"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }
}
