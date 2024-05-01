package com.ironhack.locmgmt.service;

import com.ironhack.locmgmt.exception.EmptyListException;
import com.ironhack.locmgmt.model.enums.Languages;
import com.ironhack.locmgmt.model.enums.ProjectType;
import com.ironhack.locmgmt.model.users.ProjectManager;
import com.ironhack.locmgmt.repository.ProjectManagerRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.*;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ProjectManagerService {
    @Autowired
    private ProjectManagerRepository projectManagerRepository;

    public List<ProjectManager> getAllProjectManagers() {
    try {
        List<ProjectManager> projectManager = projectManagerRepository.findAll();
        if (projectManager.isEmpty()) {
            throw new EmptyListException("No project managers were found");
        }
        return projectManager;
        } catch (DataAccessException e) {
            throw new DataRetrievalFailureException("Error while retrieving all project managers", e);
        }
    }

    public ProjectManager getProjectManagerById(Long id) {
        return projectManagerRepository.findById(id) .orElseThrow(() -> new EntityNotFoundException("Projct manager not found with id: " + id));
    }

    public ProjectManager createProjectManager(ProjectManager projectManager) {
        try {
            // Set tasks and projects to empty lists
            projectManager.setTasks(Collections.emptyList());
            projectManager.setProjects(Collections.emptyList());

            /*Add "Project managers cannot be assigned to tasks or projects directly" if we have time*/

            return projectManagerRepository.save(projectManager);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Error while creating the project manager", e);
        }
    }

    public ProjectManager updateProjectManager(Long id, ProjectManager projectManagerDetails) {
        ProjectManager existingProjectManager = projectManagerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project manager not found with id: " + id));

        // Update the inherent Project Manager fields passed
        if (projectManagerDetails.getSpokenLanguages() != null) {
        existingProjectManager.setSpokenLanguages(projectManagerDetails.getSpokenLanguages());
        }
        if (projectManagerDetails.getProjectTypes() != null) {
            existingProjectManager.setProjectTypes(projectManagerDetails.getProjectTypes());
        }

        // Update the fields inherited from the User class
        if (projectManagerDetails.getUsername() != null) {
            existingProjectManager.setUsername(projectManagerDetails.getUsername());
        }
        if (projectManagerDetails.getName() != null) {
            existingProjectManager.setName(projectManagerDetails.getName());
        }
        if (projectManagerDetails.getEmail() != null) {
            projectManagerDetails.setEmail(projectManagerDetails.getEmail());
        }

        return projectManagerRepository.save(existingProjectManager);
    }

    /*Fix error*/
    public void deleteProjectManager(Long id) {
        try {
            projectManagerRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Rate not found with id: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Error deleting rate with id: " + id);
        }
    }

    public List<ProjectManager> findProjectManagersBySpokenLanguage(Languages language) {
        try {
            List<ProjectManager> projectManagers = projectManagerRepository.findBySpokenLanguages(language);
            if (projectManagers.isEmpty()) {
                throw new EmptyListException("No project managers found for the specified language");
            }
            return projectManagers;
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to retrieve project managers by spoken languages", e);
        }
    }

    public List<ProjectManager> findProjectManagersByProjectType(ProjectType projectType) {
        try {
            List<ProjectManager> projectManagers = projectManagerRepository.findByProjectTypes(projectType);
            if (projectManagers.isEmpty()) {
                throw new EmptyListException("No project managers found for the specified project type");
            }
            return projectManagers;
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to retrieve project managers by project types", e);
        }
    }
}
