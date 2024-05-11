package com.ironhack.locmgmt.service;

import com.ironhack.locmgmt.exception.EmptyListException;
import com.ironhack.locmgmt.model.Client;
import com.ironhack.locmgmt.repository.ClientRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;

import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ClientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientService.class);

    @Autowired
    private ClientRepository clientRepository;

    public List<Client> getAllClients() {
        try {
            List<Client> clients = clientRepository.findAll();
        if (clients.isEmpty()){
            throw new EmptyListException("No clients were found");
        }
            return clients;
        } catch (DataAccessException e) {
                throw new DataRetrievalFailureException("Error while retrieving all clients", e);
        }
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + id));
    }

    public Client createClient(Client client) {
        try {
            //Check if projects are being set directly
            /*if (!client.getProjects().isEmpty()) {
                LOGGER.warn("Clients cannot be assigned to projects directly. Add the information in the project itself.");
            }*/

            //Set projects to empty lists -> can only be assigned through projects
            client.setProjects(Collections.emptyList());

            return clientRepository.save(client);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Error while creating the client", e);
        }
    }

    public Client updateClient(Long clientId, Client clientDetails) {
        Client existingClient = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + clientId));

        //Only updates fields provided in clientDetails
        if (clientDetails.getName() != null) {
            existingClient.setName(clientDetails.getName());
        }
        if (clientDetails.getEmail() != null) {
            existingClient.setEmail(clientDetails.getEmail());
        }
        if (clientDetails.getVATNumber() != null) {
            existingClient.setVATNumber(clientDetails.getVATNumber());
        }
        if (clientDetails.getAddress() != null) {
            existingClient.setAddress(clientDetails.getAddress());
        }

        return clientRepository.save(existingClient);
    }

    public void deleteClient(Long clientId) {
        try {clientRepository.deleteById(clientId);}
        catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Client not found with id: " + clientId);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Error deleting client with id: " + clientId);
        }
    }

    public List<Client> getClientByName(String name) {
        List<Client> clients = clientRepository.getClientByName(name);
        if (clients.isEmpty()) {
            throw new EmptyListException("No clients found with name: " + name);
        }
        return clients;
    }

    public List<String> getClientEmailByName(String name) {
        List<String> emails = clientRepository.findEmailByName(name);
        if (emails.isEmpty()) {
            throw new EmptyListException("No client emails found with name: " + name);
        }
        return emails;
    }
}