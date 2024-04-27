package com.ironhack.locmgmt.service;

import com.ironhack.locmgmt.model.projects.Project;
import com.ironhack.locmgmt.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public void deleteProject(Long ProjectId) {
        projectRepository.deleteById(ProjectId);
    }

}
