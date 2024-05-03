package com.ironhack.locmgmt.service;

import com.ironhack.locmgmt.exception.EmptyListException;
import com.ironhack.locmgmt.model.Client;
import com.ironhack.locmgmt.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.*;

import jakarta.persistence.EntityNotFoundException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClientServiceTest {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientRepository clientRepository;

    @Test
    void createClient_Success() {
        Client newClient = new Client("New Client", "client@example.com", "123456789", "123 Main St");

        Client createdClient = clientService.createClient(newClient);

        assertNotNull(createdClient);
        assertNotNull(createdClient.getId());
        assertEquals(newClient.getName(), createdClient.getName());
    }

    @Test
    void updateClient_Success() {
        Client newClient = new Client("New Client", "client@example.com", "123456789", "123 Main St");
        Client savedClient = clientRepository.save(newClient);

        Client updatedClient = new Client("Updated Client", "updated@example.com", "987654321", "456 Second St");

        Client result = clientService.updateClient(savedClient.getId(), updatedClient);

        assertNotNull(result);
        assertEquals(updatedClient.getName(), result.getName());
        assertEquals(updatedClient.getEmail(), result.getEmail());
        assertEquals(updatedClient.getVATNumber(), result.getVATNumber());
        assertEquals(updatedClient.getAddress(), result.getAddress());
    }

    @Test
    void deleteClient_Success() {
        Client newClient = new Client("New Client", "client@example.com", "123456789", "123 Main St");
        Client savedClient = clientRepository.save(newClient);

        clientService.deleteClient(savedClient.getId());

        assertThrows(EntityNotFoundException.class, () -> clientService.getClientById(savedClient.getId()));
    }

    @Test
    void getAllClients_Success() {
        Client newClient = new Client("New Client", "client@example.com", "123456789", "123 Main St");
        clientRepository.save(newClient);

        List<Client> clients = clientService.getAllClients();

        assertFalse(clients.isEmpty());
    }

    @Test
    void getClientById_Success() {
        Client newClient = new Client("New Client", "client@example.com", "123456789", "123 Main St");
        Client savedClient = clientRepository.save(newClient);

        Client retrievedClient = clientService.getClientById(savedClient.getId());

        assertNotNull(retrievedClient);
        assertEquals(savedClient.getId(), retrievedClient.getId());
    }

    @Test
    void getClientByName_Success() {
        String name = "New Client";
        Client newClient = new Client(name, "client@example.com", "123456789", "123 Main St");
        clientRepository.save(newClient);

        List<Client> clients = clientService.getClientByName(name);

        assertFalse(clients.isEmpty());
        assertTrue(clients.stream().allMatch(client -> client.getName().equals(name)));
    }
}
