package com.ironhack.locmgmt.service;

import com.ironhack.locmgmt.exception.EmptyListException;
import com.ironhack.locmgmt.model.users.Linguist;
import com.ironhack.locmgmt.repository.LinguistRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinguistService {
    @Autowired
    private LinguistRepository linguistRepository;

    public List<Linguist> getAllLinguists() {
        try {
            List<Linguist> linguists = linguistRepository.findAll();;
            if (linguists.isEmpty()) {
                throw new EmptyListException("No linguists were found");
            }
            return linguists;
        } catch (DataAccessException e) {
            throw new DataRetrievalFailureException("Error while retrieving all rates", e);
        }
    }

    public Linguist getLinguistById(Long id) {
        return linguistRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Linguist not found with id: " + id));

    }

    public Linguist createLinguist(Linguist linguist) {
        try {
            return linguistRepository.save(linguist);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Error while creating the linguist", e);
        }
    }

    public Linguist updateLinguist(Long id, Linguist linguistDetails) {
        Linguist existingLinguist = linguistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Linguist not found with id: " + id));

        // Update the inherent Admin fields passed
        if (linguistDetails.getLanguages() != null) {
            existingLinguist.setLanguages(linguistDetails.getLanguages());
        }

        if (linguistDetails.getProjectTypes() != null) {
            existingLinguist.setProjectTypes(linguistDetails.getProjectTypes());
        }

        if (linguistDetails.getDtpTechnologies() != null) {
            existingLinguist.setDtpTechnologies(linguistDetails.getDtpTechnologies());
        }

        if (linguistDetails.getLinguisticTechnologies() != null) {
            existingLinguist.setLinguisticTechnologies(linguistDetails.getLinguisticTechnologies());
        }

        // Update the fields inherited from the User class
        if (linguistDetails.getUsername() != null) {
            existingLinguist.setUsername(linguistDetails.getUsername());
        }
        if (linguistDetails.getName() != null) {
            existingLinguist.setName(linguistDetails.getName());
        }
        if (linguistDetails.getEmail() != null) {
            existingLinguist.setEmail(linguistDetails.getEmail());
        }
/*
        existingLinguist.setRates(linguistDetails.getRates());
*/

        return linguistRepository.save(existingLinguist);
    }

    public void deleteLinguist(Long id) {
        try {
            linguistRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Linguist not found with id: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Linguist deleting rate with id: " + id);
        }
    }
}
