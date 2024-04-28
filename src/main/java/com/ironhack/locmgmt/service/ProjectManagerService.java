package com.ironhack.locmgmt.service;

import com.ironhack.locmgmt.exception.EmptyListException;
import com.ironhack.locmgmt.model.users.ProjectManager;
import com.ironhack.locmgmt.repository.ProjectManagerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

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
            return projectManagerRepository.save(projectManager);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Error while creating the project manager", e);
        }
    }

    public ProjectManager updateProjectManager(Long id, ProjectManager projectManagerDetails) {
        ProjectManager existingProjectManager = projectManagerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project manager not found with id: " + id));

        // Update the inherent Project Manager fields passed
        if (existingProjectManager.getSpokenLanguages() != null) {
        existingProjectManager.setSpokenLanguages(projectManagerDetails.getSpokenLanguages());
        }

        if (existingProjectManager.getProjectTypes() != null) {
            existingProjectManager.setProjectTypes(projectManagerDetails.getProjectTypes());
        }

        // Update the fields inherited from the User class
        if (projectManagerDetails.getUsername() != null) {
            existingProjectManager.setUsername(projectManagerDetails.getUsername());
        }
        if (existingProjectManager.getName() != null) {
            existingProjectManager.setName(projectManagerDetails.getName());
        }
        if (existingProjectManager.getEmail() != null) {
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
}
