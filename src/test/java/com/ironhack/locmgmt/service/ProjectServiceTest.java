package com.ironhack.locmgmt.service;

import com.ironhack.locmgmt.exception.EmptyListException;
import com.ironhack.locmgmt.model.enums.Status;
import org.springframework.dao.DataAccessException;
import jakarta.persistence.EntityNotFoundException;

import com.ironhack.locmgmt.model.projects.Project;
import com.ironhack.locmgmt.model.enums.ProjectType;
import com.ironhack.locmgmt.repository.ProjectRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProjectServiceTest {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectRepository projectRepository;

    private Project project1;
    private Project project2;

    @BeforeEach
    void setUp() {
        project1 = new Project("Project 1", "Description", new Date(), new Date(), BigDecimal.valueOf(1000), Status.STARTED, ProjectType.LINGUISTIC);
        project2 = new Project("Project 2", "Description", new Date(), new Date(), BigDecimal.valueOf(1500), Status.STARTED, ProjectType.LINGUISTIC);
        projectRepository.saveAll(List.of(project1, project2));
    }

    @Test
    void deleteProject_Valid() {
        projectService.deleteProject(project1.getId());
        assertFalse(projectRepository.existsById(project1.getId()));
    }

    @Test
    void deleteProject_EntityNotFoundException() {
        assertThrows(EntityNotFoundException.class, () -> projectService.deleteProject(9999L));
    }

    @Test
    void getAllProjects_Valid() {
        List<Project> projects = projectService.getAllProjects();
        assertFalse(projects.isEmpty());
        assertEquals(2, projects.size());
    }

    @Test
    void getAllProjects_EmptyListException() {
        projectRepository.deleteAll();
        assertThrows(EmptyListException.class, () -> projectService.getAllProjects());
    }

    @Test
    void getProjectById_Valid() {
        Project foundProject = projectService.getProjectById(project1.getId());
        assertNotNull(foundProject);
        assertEquals(project1.getName(), foundProject.getName());
    }

    @Test
    void getProjectById_EntityNotFoundException() {
        assertThrows(EntityNotFoundException.class, () -> projectService.getProjectById(100L));
    }
}