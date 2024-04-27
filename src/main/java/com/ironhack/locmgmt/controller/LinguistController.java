package com.ironhack.locmgmt.controller;

import com.ironhack.locmgmt.model.users.Linguist;
import com.ironhack.locmgmt.service.LinguistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LinguistController {
    @Autowired
    private LinguistService linguistService;

    @GetMapping("/linguists")
    public ResponseEntity<List<Linguist>> getAllClients() {
        List<Linguist> linguists = linguistService.getAllLinguists();
        return new ResponseEntity<>(linguists, HttpStatus.OK);
    }

    @GetMapping("/linguist/{id}")
    public ResponseEntity<Linguist> getLinguistById(@PathVariable Long id) {
        Linguist linguist = linguistService.getLinguistById(id);
        if (linguist != null) {
            return new ResponseEntity<>(linguist, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/saveLinguist")
    public ResponseEntity<Linguist> createLinguist(@RequestBody Linguist linguist) {
        Linguist createdLinguist = linguistService.createLinguist(linguist);
        return new ResponseEntity<>(createdLinguist, HttpStatus.CREATED);
    }

    @PutMapping("/updateLinguist/{id}")
    public ResponseEntity<Linguist> updateLinguist(@PathVariable Long id, @RequestBody Linguist linguist) {
        Linguist updatedLinguist = linguistService.updateLinguist(id, linguist);
        return new ResponseEntity<>(updatedLinguist, HttpStatus.OK);
    }

    @DeleteMapping("/deleteLinguist/{id}")
    public ResponseEntity<?> deleteLinguist(@PathVariable Long id) {
        linguistService.deleteLinguist(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
