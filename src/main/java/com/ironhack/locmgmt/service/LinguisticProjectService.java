package com.ironhack.locmgmt.service;

import com.ironhack.locmgmt.exception.EmptyListException;
import com.ironhack.locmgmt.model.enums.LinguisticTechnology;
import com.ironhack.locmgmt.model.enums.Status;
import com.ironhack.locmgmt.model.projects.DTPProject;
import com.ironhack.locmgmt.model.projects.LinguisticProject;
import com.ironhack.locmgmt.repository.LinguisticProjectRepository;
import com.ironhack.locmgmt.util.ProjectUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.ironhack.locmgmt.util.ProjectUtil.updateProjectDates;

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

    public LinguisticProject createLinguisticProject(LinguisticProject linguisticProject) {
        //Checks if there is already a project with the same name
        Optional<LinguisticProject> existingProject = linguisticProjectRepository.findByName(linguisticProject.getName());
        if (existingProject.isPresent()) {
            throw new DataIntegrityViolationException("A project with the same name already exists");
        }

        //Sets tasks to empty lists
        linguisticProject.setTasks(Collections.emptyList());

        /*Add "Projects cannot be assigned directly to tasks or linguists" if we have time*/

        //Sets projectStatus to NOT if info not passed by the user when creating project
        linguisticProject.setProjectStatus(linguisticProject.getProjectStatus() != null ? linguisticProject.getProjectStatus() : Status.NOT_STARTED);

        //Update project dates and time remaining
        ProjectUtil.updateProjectDates(linguisticProject);

        // Calculate total words
        ProjectUtil.calculateTotalWords(linguisticProject);

        try {
            return linguisticProjectRepository.save(linguisticProject);
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
            ProjectUtil.calculateTotalWords(existingLinguisticProject);
        }
        if (linguisticProjectDetails.getFuzzyWords() != null) {
            existingLinguisticProject.setFuzzyWords(linguisticProjectDetails.getFuzzyWords());
            ProjectUtil.calculateTotalWords(existingLinguisticProject);
        }
        if (linguisticProjectDetails.getLinguisticTechnology() != null) {
            existingLinguisticProject.setLinguisticTechnology(linguisticProjectDetails.getLinguisticTechnology());
        }

        //Update the fields inherited from the Project class
        if (linguisticProjectDetails.getName() != null) {
            Optional<LinguisticProject> existingProjectWithSameName = linguisticProjectRepository.findByName(linguisticProjectDetails.getName());
            if (existingProjectWithSameName.isPresent()) {
                throw new DataIntegrityViolationException("A project with the same name already exists");
            }
            existingLinguisticProject.setName(linguisticProjectDetails.getName());
        }
        if (linguisticProjectDetails.getDescription() != null) {
            existingLinguisticProject.setDescription(linguisticProjectDetails.getDescription());
        }
        if (linguisticProjectDetails.getDeadline() != null) {
            existingLinguisticProject.setDeadline(linguisticProjectDetails.getDeadline());
            ProjectUtil.updateTimeRemaining(existingLinguisticProject);
        }
        if (linguisticProjectDetails.getProjectStatus() != null) {
            existingLinguisticProject.setProjectStatus(linguisticProjectDetails.getProjectStatus());
            updateProjectDates(existingLinguisticProject);
        }
        if (linguisticProjectDetails.getTotalBudget() != null) {
            existingLinguisticProject.setTotalBudget(linguisticProjectDetails.getTotalBudget());
        }
        if (linguisticProjectDetails.getProjectStatus() != null) {
            existingLinguisticProject.setProjectStatus(linguisticProjectDetails.getProjectStatus());
        }
        //Add client when updating project
        if (linguisticProjectDetails.getClient() != null) {
            existingLinguisticProject.setClient(linguisticProjectDetails.getClient());
        }
        //Add project manager when updating DTP project
        if (linguisticProjectDetails.getProjectManager() != null) {
            existingLinguisticProject.setProjectManager(linguisticProjectDetails.getProjectManager());
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

    public List<LinguisticProject> findByNewWordsGreaterThanAndFuzzyWordsGreaterThan(Integer newWords, Integer fuzzyWords) {
        if (newWords == null || fuzzyWords == null) {
            throw new IllegalArgumentException("NewWords and FuzzyWords must be provided.");
        }
        List<LinguisticProject> projects = linguisticProjectRepository.findByNewWordsGreaterThanAndFuzzyWordsGreaterThan(newWords, fuzzyWords);
        if (projects.isEmpty()) {
            throw new EmptyListException("No linguistic projects found with provided criteria.");
        }
        return projects;
    }

    public List<LinguisticProject> findByNewWordsLessThanAndFuzzyWordsLessThan(Integer newWords, Integer fuzzyWords) {
        if (newWords == null || fuzzyWords == null) {
            throw new IllegalArgumentException("NewWords and FuzzyWords must be provided.");
        }
        List<LinguisticProject> projects = linguisticProjectRepository.findByNewWordsLessThanAndFuzzyWordsLessThan(newWords, fuzzyWords);
        if (projects.isEmpty()) {
            throw new EmptyListException("No linguistic projects found with provided criteria.");
        }
        return projects;
    }

    public List<LinguisticProject> findByTotalWordsGreaterThan(Integer totalWords) {
        if (totalWords == null) {
            throw new IllegalArgumentException("TotalWords must be provided.");
        }
        List<LinguisticProject> projects = linguisticProjectRepository.findByTotalWordsGreaterThan(totalWords);
        if (projects.isEmpty()) {
            throw new EmptyListException("No linguistic projects found with provided criteria.");
        }
        return projects;
    }

    public List<LinguisticProject> findByTotalWordsLessThan(Integer totalWords) {
        if (totalWords == null) {
            throw new IllegalArgumentException("TotalWords must be provided.");
        }
        List<LinguisticProject> projects = linguisticProjectRepository.findByTotalWordsLessThan(totalWords);
        if (projects.isEmpty()) {
            throw new EmptyListException("No linguistic projects found with provided criteria.");
        }
        return projects;
    }

    public List<LinguisticProject> findByTotalBudgetGreaterThan(BigDecimal totalBudget) {
        if (totalBudget == null) {
            throw new IllegalArgumentException("TotalBudget must be provided.");
        }
        List<LinguisticProject> projects = linguisticProjectRepository.findByTotalBudgetGreaterThan(totalBudget);
        if (projects.isEmpty()) {
            throw new EmptyListException("No linguistic projects found with provided criteria.");
        }
        return projects;
    }

    public List<LinguisticProject> findByTotalBudgetLessThan(BigDecimal totalBudget) {
        if (totalBudget == null) {
            throw new IllegalArgumentException("TotalBudget must be provided.");
        }
        List<LinguisticProject> projects = linguisticProjectRepository.findByTotalBudgetLessThan(totalBudget);
        if (projects.isEmpty()) {
            throw new EmptyListException("No linguistic projects found with provided criteria.");
        }
        return projects;
    }

    public List<LinguisticProject> findByLinguisticTechnology(LinguisticTechnology linguisticTechnology) {
        if (linguisticTechnology == null) {
            throw new IllegalArgumentException("LinguisticTechnology must be provided.");
        }
        List<LinguisticProject> projects = linguisticProjectRepository.findByLinguisticTechnology(linguisticTechnology);
        if (projects.isEmpty()) {
            throw new EmptyListException("No linguistic projects found with provided criteria.");
        }
        return projects;
    }
}
