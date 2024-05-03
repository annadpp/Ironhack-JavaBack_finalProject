package com.ironhack.locmgmt.repository;

import com.ironhack.locmgmt.model.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    void getClientByName() {
        Client client = new Client("Test Client", "test@example.com", "123456789", "Test Address");
        clientRepository.save(client);

        List<Client> foundClients = clientRepository.getClientByName("Test Client");

        assertEquals(1, foundClients.size());
        assertEquals("Test Client", foundClients.get(0).getName());
    }

    @Test
    void findEmailByName() {
        Client client = new Client("Test Client", "test@example.com", "123456789", "Test Address");
        clientRepository.save(client);

        List<String> foundEmails = clientRepository.findEmailByName("Test Client");

        assertEquals(1, foundEmails.size());
        assertEquals("test@example.com", foundEmails.get(0));
    }
}
