package com.ironhack.locmgmt.service;

import com.ironhack.locmgmt.model.users.Linguist;
import com.ironhack.locmgmt.repository.LinguistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinguistService {
    @Autowired
    private LinguistRepository linguistRepository;

    public List<Linguist> getAllLinguists() {
        return linguistRepository.findAll();
    }

    public Linguist getLinguistById(Long id) {
        return linguistRepository.findById(id).orElse(null);
    }

    public Linguist createLinguist(Linguist linguist) {
        return linguistRepository.save(linguist);
    }

    public Linguist updateLinguist(Long linguistId, Linguist linguistDetails) {
        Linguist existingLinguist = linguistRepository.findById(linguistId)
                .orElseThrow(() -> new RuntimeException("Linguist not found with id: " + linguistId));

        existingLinguist.setLanguages(linguistDetails.getLanguages());
        existingLinguist.setProjectTypes(linguistDetails.getProjectTypes());
        existingLinguist.setDtpTechnologies(linguistDetails.getDtpTechnologies());
        existingLinguist.setLinguisticTechnologies(linguistDetails.getLinguisticTechnologies());
/*
        existingLinguist.setRates(linguistDetails.getRates());
*/

        return linguistRepository.save(existingLinguist);
    }

    public void deleteLinguist(Long LinguistId) {
        linguistRepository.deleteById(LinguistId);
    }
}
