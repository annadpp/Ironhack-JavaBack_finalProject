package com.ironhack.locmgmt.service;

import com.ironhack.locmgmt.exception.EmptyListException;
import com.ironhack.locmgmt.model.enums.DTPTechnology;
import com.ironhack.locmgmt.model.enums.Languages;
import com.ironhack.locmgmt.model.enums.LinguisticTechnology;
import com.ironhack.locmgmt.model.enums.ProjectType;
import com.ironhack.locmgmt.model.users.Linguist;
import com.ironhack.locmgmt.repository.LinguistRepository;

import jakarta.persistence.EntityNotFoundException;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.*;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
            // Set rates, tasks and projects to empty lists
/*
            linguist.setProjects(Collections.emptyList());
*/
            linguist.setTasks(Collections.emptyList());
            linguist.setRates(Collections.emptyList());

            /*Add "Linguists cannot be assigned to rates, tasks or projects directly" if we have time*/

            return linguistRepository.save(linguist);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Error while creating the linguist", e);
        }
    }

    public Linguist updateLinguist(Long id, Linguist linguistDetails) {
        Linguist existingLinguist = linguistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Linguist not found with id: " + id));

        // Update the inherent Admin fields passed
        if (linguistDetails.getSourceLanguages() != null) {
            existingLinguist.setSourceLanguages(linguistDetails.getSourceLanguages());
        }

        if (linguistDetails.getTargetLanguages() != null) {
            existingLinguist.setTargetLanguages(linguistDetails.getTargetLanguages());
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

    /*Fix error*/
    public void deleteLinguist(Long id) {
        try {
            linguistRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Linguist not found with id: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Linguist deleting rate with id: " + id);
        }
    }

    public List<Linguist> findBySourceLanguages(Languages sourceLanguages) {
        try {
            List<Linguist> linguists = linguistRepository.findBySourceLanguages(sourceLanguages);
            if (linguists.isEmpty()) {
                throw new EmptyListException("No linguists were found");
            }
            return linguists;
        } catch (Exception e) {
            throw new ServiceException("Error occurred while fetching linguists by source language", e);
        }
    }

    public List<Linguist> findByTargetLanguages(Languages targetLanguages) {
        try {
            List<Linguist> linguists = linguistRepository.findByTargetLanguages(targetLanguages);
            if (linguists.isEmpty()) {
                throw new EmptyListException("No linguists were found");
            }
            return linguists;
        } catch (Exception e) {
            throw new ServiceException("Error occurred while fetching linguists by target language", e);
        }
    }

    public List<Linguist> findByProjectTypes(ProjectType projectType) {
        try {
            List<Linguist> linguists = linguistRepository.findByProjectTypes(projectType);
            if (linguists.isEmpty()) {
                throw new EmptyListException("No linguists were found");
            }
            return linguists;
        } catch (Exception e) {
            throw new ServiceException("Error occurred while fetching linguists by project type", e);
        }
    }

    public List<Linguist> findByLinguisticTechnologies(LinguisticTechnology linguisticTechnology) {
        try {
            List<Linguist> linguists = linguistRepository.findByLinguisticTechnologies(linguisticTechnology);
            if (linguists.isEmpty()) {
                throw new EmptyListException("No linguists were found");
            }
            return linguists;
        } catch (Exception e) {
            throw new ServiceException("Error occurred while fetching linguists by linguistic technology", e);
        }
    }

    public List<Linguist> findByDtpTechnologies(DTPTechnology dtpTechnology) {
        try {
            List<Linguist> linguists = linguistRepository.findByDtpTechnologies(dtpTechnology);
            if (linguists.isEmpty()) {
                throw new EmptyListException("No linguists were found");
            }
            return linguists;
        } catch (Exception e) {
            throw new ServiceException("Error occurred while fetching linguists by DTP technology", e);
        }
    }

    public List<Linguist> findBySourceLanguagesAndTargetLanguages(Languages sourceLanguage, Languages targetLanguage) {
        try {
            List<Linguist> linguists = linguistRepository.findBySourceLanguagesAndTargetLanguages(sourceLanguage, targetLanguage);
            if (linguists.isEmpty()) {
                throw new EmptyListException("No linguists were found");
            }
            return linguists;
        } catch (Exception e) {
            throw new ServiceException("Error occurred while fetching linguists by source and target language", e);
        }
    }

    public List<Linguist> findBySourceLanguagesAndTargetLanguagesAndProjectTypes(Languages sourceLanguages, Languages targetLanguages, ProjectType projectType) {
        try {
            List<Linguist> linguists = linguistRepository.findBySourceLanguagesAndTargetLanguagesAndProjectTypes(sourceLanguages, targetLanguages, projectType);
            if (linguists.isEmpty()) {
                throw new EmptyListException("No linguists were found");
            }
            return linguists;
        } catch (Exception e) {
            throw new ServiceException("Error occurred while fetching linguists by source, target language, and project type", e);
        }
    }

    public List<Linguist> findBySourceLanguagesAndTargetLanguagesAndLinguisticTechnologies(Languages sourceLanguages, Languages targetLanguages, LinguisticTechnology linguisticTechnology) {
        try {
            List<Linguist> linguists = linguistRepository.findBySourceLanguagesAndTargetLanguagesAndLinguisticTechnologies(sourceLanguages, targetLanguages, linguisticTechnology);
            if (linguists.isEmpty()) {
                throw new EmptyListException("No linguists were found");
            }
            return linguists;
        } catch (Exception e) {
            throw new ServiceException("Error occurred while fetching linguists by source, target language, and linguistic technology", e);
        }
    }

    public List<Linguist> findBySourceLanguagesAndTargetLanguagesAndDtpTechnologies(Languages sourceLanguages, Languages targetLanguages, DTPTechnology dtpTechnology) {
        try {
            List<Linguist> linguists = linguistRepository.findBySourceLanguagesAndTargetLanguagesAndDtpTechnologies(sourceLanguages, targetLanguages, dtpTechnology);
            if (linguists.isEmpty()) {
                throw new EmptyListException("No linguists were found");
            }
            return linguists;
        } catch (Exception e) {
            throw new ServiceException("Error occurred while fetching linguists by source, target language, and DTP technology", e);
        }
    }

    public List<Linguist> findByLinguisticTechnologiesAndProjectTypes(LinguisticTechnology linguisticTechnology, ProjectType projectType) {
        try {
            List<Linguist> linguists = linguistRepository.findByLinguisticTechnologiesAndProjectTypes(linguisticTechnology, projectType);
            if (linguists.isEmpty()) {
                throw new EmptyListException("No linguists were found");
            }
            return linguists;
        } catch (Exception e) {
            throw new ServiceException("Error occurred while fetching linguists by linguistic technology and project type", e);
        }
    }

    public List<Linguist> findByDtpTechnologiesAndProjectTypes(DTPTechnology dtpTechnology, ProjectType projectType) {
        try {
            List<Linguist> linguists = linguistRepository.findByDtpTechnologiesAndProjectTypes(dtpTechnology, projectType);
            if (linguists.isEmpty()) {
                throw new EmptyListException("No linguists were found");
            }
            return linguists;
        } catch (Exception e) {
            throw new ServiceException("Error occurred while fetching linguists by DTP technology and project type", e);
        }
    }
}
