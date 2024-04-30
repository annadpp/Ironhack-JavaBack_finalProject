package com.ironhack.locmgmt.service;

import com.ironhack.locmgmt.exception.EmptyListException;
import com.ironhack.locmgmt.model.projects.LinguisticProject;
import com.ironhack.locmgmt.repository.LinguisticProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinguisticProjectService {

    @Autowired
    private LinguisticProjectRepository linguisticProjectRepository;

    public List<LinguisticProject> getAllLinguisticProjects() {
        try {List<LinguisticProject> linguisticProjects = linguisticProjectRepository.findAll();
            if (linguisticProjects.isEmpty()) {
                throw new EmptyListException("No Linguistic projects were found");
            }
            return linguisticProjects;
        } catch (DataAccessException e) {
            throw new DataRetrievalFailureException("Error while retrieving all Linguistic projects", e);
        }
    }

    public LinguisticProject getLinguisticProjectById(Long id) {
        return linguisticProjectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Admin not found with id: " + id));
    }

    /*projectstatus base not_started*/
    public LinguisticProject createLinguisticProject(LinguisticProject DTPProject) {
        try {
            return linguisticProjectRepository.save(DTPProject);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Error while creating the Linguistic project", e);
        }
    }

    public LinguisticProject updateLinguisticProject(Long linguisticProjectId, LinguisticProject linguisticProjectDetails) {
        LinguisticProject existingLinguisticProject = linguisticProjectRepository.findById(linguisticProjectId)
                .orElseThrow(() -> new RuntimeException("Linguistic project not found with id: " + linguisticProjectId));

        //Update the inherent DTP Project fields passed
        if (linguisticProjectDetails.getNewWords() != null) {
            existingLinguisticProject.setNewWords(linguisticProjectDetails.getNewWords());
        }
        if (linguisticProjectDetails.getFuzzyWords() != null) {
            existingLinguisticProject.setFuzzyWords(linguisticProjectDetails.getFuzzyWords());
        }
        if (linguisticProjectDetails.getLinguisticTechnology() != null) {
            existingLinguisticProject.setLinguisticTechnology(linguisticProjectDetails.getLinguisticTechnology());
        }

        //Update the fields inherited from the Project class
        if (linguisticProjectDetails.getName() != null) {
            existingLinguisticProject.setName(linguisticProjectDetails.getName());
        }
        if (linguisticProjectDetails.getDescription() != null) {
            existingLinguisticProject.setDescription(linguisticProjectDetails.getDescription());
        }
        if (linguisticProjectDetails.getTotalBudget() != null) {
            existingLinguisticProject.setTotalBudget(linguisticProjectDetails.getTotalBudget());
        }
        if (linguisticProjectDetails.getProjectStatus() != null) {
            existingLinguisticProject.setProjectStatus(linguisticProjectDetails.getProjectStatus());
        }
        if (linguisticProjectDetails.getSourceLanguage() != null) {
            existingLinguisticProject.setSourceLanguage(linguisticProjectDetails.getSourceLanguage());
        }
        if (linguisticProjectDetails.getTargetLanguages() != null) {
            existingLinguisticProject.setTargetLanguages(linguisticProjectDetails.getTargetLanguages());
        }

        return linguisticProjectRepository.save(existingLinguisticProject);
    }

    /*Fix error*/
    public void deleteLinguisticProject(Long id) {
        try {
            linguisticProjectRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Linguistic project not found with id: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Error deleting Linguistic project with id: " + id);
        }
    }
}
