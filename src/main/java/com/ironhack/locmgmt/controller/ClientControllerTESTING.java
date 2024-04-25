package com.ironhack.locmgmt.controller;

import com.ironhack.locmgmt.model.Client;
import com.ironhack.locmgmt.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClientControllerTESTING {
    @Autowired
    ClientRepository clientRepository;

    @GetMapping(value = "/")
    public String holaMundo(){
        return "Hola mundo";
    }

    @GetMapping(value = "/clients")
    public List<Client> getTasks(){
        return clientRepository.findAll();
    }

    @PostMapping(value="/saveClient")
    public String saveTask(@RequestBody Client client){
        clientRepository.save(client);
        return "Task saved";
    }

    @DeleteMapping(value="/delete/{id}")
    public String deleteClient(@PathVariable long id){
        Client deletedClient = clientRepository.findById(id).get();
        clientRepository.delete(deletedClient);
        return "Task deleted";
    }
}
