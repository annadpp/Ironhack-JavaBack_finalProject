package com.ironhack.locmgmt.repository;

import com.ironhack.locmgmt.model.enums.ProjectType;
import com.ironhack.locmgmt.model.projects.Project;
import com.ironhack.locmgmt.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void testSaveProject() {
        Project project = new Project("Project 1", "Description 1", null, null, BigDecimal.TEN, null, ProjectType.TRANSLATION);
        projectRepository.save(project);

        Project savedProject = projectRepository.findById(project.getId()).orElse(null);

        assertNotNull(savedProject);
        assertEquals("Project 1", savedProject.getName());
    }

    @Test
    void testFindAllProjects() {
        // Create some sample projects
        Project project1 = new Project("Project 1", "Description 1", null, null, BigDecimal.TEN, null, ProjectType.TRANSLATION);
        Project project2 = new Project("Project 2", "Description 2", null, null, BigDecimal.TEN, null, ProjectType.TRANSLATION);
        projectRepository.saveAll(List.of(project1, project2));

        List<Project> projects = projectRepository.findAll();

        assertEquals(2, projects.size());
    }
}
