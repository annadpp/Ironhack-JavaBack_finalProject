package com.ironhack.locmgmt.controller;

import com.ironhack.locmgmt.model.users.Linguist;
import com.ironhack.locmgmt.service.LinguistService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/linguists")
public class LinguistController {
    @Autowired
    private LinguistService linguistService;

    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    public List<Linguist> getAllClients() {
        return linguistService.getAllLinguists();
    }

    @GetMapping("/get/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Linguist getLinguistById(@PathVariable Long id) {
        Linguist linguist = linguistService.getLinguistById(id);
        if (linguist == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Linguist not found");
        }
        return linguist;
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Linguist createLinguist(@RequestBody Linguist linguist) {
        return linguistService.createLinguist(linguist);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Linguist updateLinguist(@PathVariable Long id, @RequestBody Linguist linguist) {
        return linguistService.updateLinguist(id, linguist);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLinguist(@PathVariable Long id) {
        linguistService.deleteLinguist(id);
    }
}
