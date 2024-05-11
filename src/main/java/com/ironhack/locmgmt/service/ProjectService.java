package com.ironhack.locmgmt.service;

import com.ironhack.locmgmt.exception.EmptyListException;
import com.ironhack.locmgmt.model.Task;
import com.ironhack.locmgmt.model.enums.ProjectType;
import com.ironhack.locmgmt.model.projects.Project;
import com.ironhack.locmgmt.repository.ProjectRepository;
import com.ironhack.locmgmt.util.ProjectUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        try {
            if (projects.isEmpty()) {
                throw new EmptyListException("No projects were found");
            }
            for (Project project : projects) {
                ProjectUtil.calculateMargin(project);
                ProjectUtil.calculateMarginPercentage(project);
                projectRepository.save(project);
            }

            return projects;
        } catch (DataAccessException e) {
            throw new DataRetrievalFailureException("Error while retrieving all projects", e);
        }
    }

    public Project getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + id));

        ProjectUtil.calculateMargin(project);
        ProjectUtil.calculateMarginPercentage(project);
        projectRepository.save(project);

        return project;
    }

    public void deleteProject(Long id) {
        try {
            projectRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Project not found with id: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Error deleting rate with id: " + id);
        }
    }

    public List<Project> findByTotalBudgetGreaterThan(BigDecimal totalBudget) {
        if (totalBudget == null) {
            throw new IllegalArgumentException("TotalBudget must be provided.");
        }
        List<Project> projects = projectRepository.findByTotalBudgetGreaterThan(totalBudget);
        if (projects.isEmpty()) {
            throw new EmptyListException("No linguistic projects found with provided criteria.");
        }
        return projects;
    }

    public List<Project> findByTotalBudgetLessThan(BigDecimal totalBudget) {
        if (totalBudget == null) {
            throw new IllegalArgumentException("TotalBudget must be provided.");
        }
        List<Project> projects = projectRepository.findByTotalBudgetLessThan(totalBudget);
        if (projects.isEmpty()) {
            throw new EmptyListException("No linguistic projects found with provided criteria.");
        }
        return projects;
    }

}
