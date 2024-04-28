package com.ironhack.locmgmt.service;

import com.ironhack.locmgmt.model.Client;
import com.ironhack.locmgmt.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public Client updateClient(Long clientId, Client clientDetails) {
        Client existingClient = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + clientId));

        existingClient.setName(clientDetails.getName());
        existingClient.setEmail(clientDetails.getEmail());
        existingClient.setVATNumber(clientDetails.getVATNumber());
        existingClient.setAddress(clientDetails.getAddress());

        return clientRepository.save(existingClient);
    }

    public void deleteClient(Long clientId) {
        clientRepository.deleteById(clientId);
    }

    public List<Client> getClientByName(String name) {
        return clientRepository.getClientByName(name);
    }

    public List<String> getClientEmailByName(String name) {
        return clientRepository.findEmailByName(name);
    }
}