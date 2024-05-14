package com.ironhack.locmgmt.service;

import com.ironhack.locmgmt.exception.EmptyListException;
import com.ironhack.locmgmt.model.enums.LinguisticTechnology;
import com.ironhack.locmgmt.model.enums.Status;
import com.ironhack.locmgmt.model.projects.LinguisticProject;
import com.ironhack.locmgmt.repository.LinguisticProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LinguisticProjectServiceTest {

    @Autowired
    private LinguisticProjectService linguisticProjectService;

    @Autowired
    private LinguisticProjectRepository linguisticProjectRepository;

    @Test
    void createLinguisticProject_Success() {
        LinguisticProject linguisticProject = new LinguisticProject("Project2", "Description", new Date(), new Date(new Date().getTime() + 10 * 24 * 60 * 60 * 1000),
                BigDecimal.valueOf(1000), Status.NOT_STARTED, LinguisticTechnology.EMAIL);

        LinguisticProject createdProject = linguisticProjectService.createLinguisticProject(linguisticProject);

        assertNotNull(createdProject);
        assertNotNull(createdProject.getId());
        assertEquals("Project2", createdProject.getName());
    }

    @Test
    void updateLinguisticProject_Success() {
        LinguisticProject linguisticProject = new LinguisticProject("Project", "Description", new Date(), new Date(new Date().getTime() + 10 * 24 * 60 * 60 * 1000),
                BigDecimal.valueOf(1000), Status.NOT_STARTED, LinguisticTechnology.EMAIL);
        LinguisticProject savedProject = linguisticProjectRepository.save(linguisticProject);

        savedProject.setName("Updated Project");
        LinguisticProject updatedProject = linguisticProjectService.updateLinguisticProject(savedProject.getId(), savedProject);

        assertNotNull(updatedProject);
        assertEquals("Updated Project", updatedProject.getName());
    }

    @Test
    void deleteLinguisticProject_Success() {
        LinguisticProject linguisticProject = new LinguisticProject("Project", "Description", new Date(), new Date(new Date().getTime() + 10 * 24 * 60 * 60 * 1000),
                BigDecimal.valueOf(1000), Status.NOT_STARTED, LinguisticTechnology.EMAIL);
        LinguisticProject savedProject = linguisticProjectRepository.save(linguisticProject);

        linguisticProjectService.deleteLinguisticProject(savedProject.getId());

        assertThrows(EntityNotFoundException.class, () -> linguisticProjectRepository.findById(savedProject.getId()).orElseThrow(EntityNotFoundException::new));
    }

    @Test
    void findByLinguisticTechnology_Success() {
        LinguisticProject linguisticProject = new LinguisticProject("Project", "Description", new Date(), new Date(new Date().getTime() + 10 * 24 * 60 * 60 * 1000),
                BigDecimal.valueOf(1000), Status.NOT_STARTED, LinguisticTechnology.EMAIL);
        linguisticProjectRepository.save(linguisticProject);

        List<LinguisticProject> projects = linguisticProjectService.findByLinguisticTechnology(LinguisticTechnology.EMAIL);

        assertFalse(projects.isEmpty());
        assertTrue(projects.stream().allMatch(project -> project.getLinguisticTechnology() == LinguisticTechnology.EMAIL));
    }
}
