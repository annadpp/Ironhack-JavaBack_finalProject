package com.ironhack.locmgmt.service;

import com.ironhack.locmgmt.exception.EmptyListException;
import com.ironhack.locmgmt.model.enums.DTPTechnology;
import com.ironhack.locmgmt.model.enums.ProjectType;
import com.ironhack.locmgmt.model.enums.Status;
import com.ironhack.locmgmt.model.projects.DTPProject;
import com.ironhack.locmgmt.repository.DTPProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class DTPProjectServiceTest {

    @Autowired
    private DTPProjectService dtpProjectService;

    @Autowired
    private DTPProjectRepository dtpProjectRepository;

    @Test
    void createDTPProject_ValidProject_Success() {
        DTPProject dtpProject = new DTPProject("Test Project", "Description", null, null, BigDecimal.TEN, Status.NOT_STARTED, DTPTechnology.WORD, 100);

        DTPProject createdProject = dtpProjectService.createDTPProject(dtpProject);

        assertNotNull(createdProject);
        assertEquals("Test Project", createdProject.getName());
        assertEquals("Description", createdProject.getDescription());
        assertEquals(BigDecimal.TEN, createdProject.getTotalBudget());
        assertEquals(Status.NOT_STARTED, createdProject.getProjectStatus());
        assertEquals(DTPTechnology.WORD, createdProject.getDtpTechnology());
        assertEquals(100, createdProject.getPages());
    }

    @Test
    void deleteDTPProject_ExistingId_Success() {
        DTPProject dtpProject = new DTPProject("Test Project", "Description", null, null, BigDecimal.TEN, Status.NOT_STARTED, DTPTechnology.WORD, 100);
        DTPProject savedProject = dtpProjectRepository.save(dtpProject);

        dtpProjectService.deleteDTPProject(savedProject.getId());

        assertThrows(EntityNotFoundException.class, () -> dtpProjectService.getDTPProjectById(savedProject.getId()));
    }

    @Test
    void getAllDTPProjects_NotEmptyList_Success() {
        DTPProject dtpProject = new DTPProject("Test Project", "Description", null, null, BigDecimal.TEN, Status.NOT_STARTED, DTPTechnology.WORD, 100);
        dtpProjectRepository.save(dtpProject);

        List<DTPProject> dtpProjects = dtpProjectService.getAllDTPProjects();

        assertFalse(dtpProjects.isEmpty());
        assertEquals(2, dtpProjects.size());
    }

    @Test
    void getDTPProjectById_ExistingId_Success() {
        DTPProject dtpProject = new DTPProject("Test Project", "Description", null, null, BigDecimal.TEN, Status.NOT_STARTED, DTPTechnology.WORD, 100);
        DTPProject savedProject = dtpProjectRepository.save(dtpProject);

        DTPProject retrievedProject = dtpProjectService.getDTPProjectById(savedProject.getId());

        assertNotNull(retrievedProject);
        assertEquals(savedProject.getId(), retrievedProject.getId());
        assertEquals("Test Project", retrievedProject.getName());
    }

    @Test
    void getDTPProjectById_NonExistingId_ExceptionThrown() {
        assertThrows(EntityNotFoundException.class, () -> dtpProjectService.getDTPProjectById(999L));
    }

    @Test
    void findByDtpTechnology_ValidTechnology_NotEmptyList() {
        DTPProject dtpProject = new DTPProject("Test Project", "Description", null, null, BigDecimal.TEN, Status.NOT_STARTED, DTPTechnology.WORD, 100);
        dtpProjectRepository.save(dtpProject);

        List<DTPProject> projects = dtpProjectService.findByDtpTechnology(DTPTechnology.WORD);

        assertFalse(projects.isEmpty());
    }
}
