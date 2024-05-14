package com.ironhack.locmgmt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.locmgmt.model.Client;
import com.ironhack.locmgmt.service.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*DOESN'T WORK WITH SECURITY ENABLED*/
@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientService clientService;

    @Test
    void createClient_Success() throws Exception {
        //Create a client object
        Client client = new Client();
        client.setName("John Doe");
        client.setEmail("john.doe@example.com");

        //Perform POST request to create the client
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/clients/save")
                        .content(asJsonString(client))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()) // Expect HTTP status 201
                .andReturn();

        //Verify that the response contains the name of the client
        String responseBody = mvcResult.getResponse().getContentAsString();
        assert (responseBody.contains("John Doe"));
    }

    @Test
    void getClientById_Success() throws Exception {
        //Create a client
        Client client = new Client();
        client.setName("Jane Doe");
        client.setEmail("jane.doe@example.com");
        clientService.createClient(client);

        //Perform GET request to fetch the client by id
        mockMvc.perform(MockMvcRequestBuilders.get("/clients/get/{id}", client.getId()))
                .andExpect(status().isOk()) // Expect HTTP status 200
                .andExpect(jsonPath("$.name").value(client.getName())) // Expect the correct name in the response
                .andExpect(jsonPath("$.email").value(client.getEmail())); // Expect the correct email in the response
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
