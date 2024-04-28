package com.ironhack.locmgmt.service;

import com.ironhack.locmgmt.model.users.ProjectManager;
import com.ironhack.locmgmt.repository.ProjectManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectManagerService {
    @Autowired
    private ProjectManagerRepository projectManagerRepository;

    public List<ProjectManager> getAllProjectManagers() {
        return projectManagerRepository.findAll();
    }

    public ProjectManager getProjectManagerById(Long id) {
        return projectManagerRepository.findById(id).orElse(null);
    }

    public ProjectManager createProjectManager(ProjectManager projectManager) {
        return projectManagerRepository.save(projectManager);
    }

    public ProjectManager updateProjectManager(Long projectManagerId, ProjectManager projectManagerDetails) {
        ProjectManager existingProjectManager = projectManagerRepository.findById(projectManagerId)
                .orElseThrow(() -> new RuntimeException("ProjectManager not found with id: " + projectManagerId));

        existingProjectManager.setSpokenLanguages(projectManagerDetails.getSpokenLanguages());
        existingProjectManager.setProjectTypes(projectManagerDetails.getProjectTypes());

        return projectManagerRepository.save(existingProjectManager);
    }

    public void deleteProjectManager(Long ProjectManagerId) {
        projectManagerRepository.deleteById(ProjectManagerId);
    }
}
