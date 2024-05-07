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

import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    void setUp() {
        Client client = new Client("John Doe", "john@example.com", "123ABC456", "123 Main St");
    }

    @Test
    void testGetAllClients() throws Exception {
        mockMvc.perform(get("/clients/get"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetClientById() throws Exception {
        Long clientId = 1L;  // Replace with a valid existing client ID
        mockMvc.perform(get("/clients/get/" + clientId))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateClient() throws Exception {
        String clientRequest = "{\"/* Add client request body data */\"}";
        mockMvc.perform(post("/clients/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientRequest))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdateClient() throws Exception {
        Long clientId = 1L;  // Replace with a valid existing client ID
        String updateRequest = "{\"/* Add client update request body data */\"}";
        mockMvc.perform(put("/clients/update/" + clientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateRequest))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteClient() throws Exception {
        Long clientId = 1L;  // Replace with a valid existing client ID
        mockMvc.perform(delete("/clients/delete/" + clientId))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetClientByName() throws Exception {
        String clientName = "John Doe";  // Replace with a valid client name
        mockMvc.perform(get("/clients/get/byName?name=" + clientName))
                .andExpect(status().isOk());
    }

    @Test
    void testGetClientEmailByName() throws Exception {
        String clientName = "John Doe";  // Replace with a valid client name
        mockMvc.perform(get("/clients/get/emailByName?name=" + clientName))
                .andExpect(status().isOk());
    }
}