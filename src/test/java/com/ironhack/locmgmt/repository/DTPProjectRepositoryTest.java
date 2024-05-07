package com.ironhack.locmgmt.repository;

import com.ironhack.locmgmt.model.enums.DTPTechnology;
import com.ironhack.locmgmt.model.projects.DTPProject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DTPProjectRepositoryTest {

    @Autowired
    private DTPProjectRepository dtpProjectRepository;

    @Test
    void findByDtpTechnology() {
        DTPProject dtpProject = new DTPProject("Test Project", "Test Description", new Date(), new Date(), BigDecimal.TEN, null, DTPTechnology.AFTER_EFFECTS, 100);
        dtpProjectRepository.save(dtpProject);

        List<DTPProject> foundProjects = dtpProjectRepository.findByDtpTechnology(DTPTechnology.AFTER_EFFECTS);

        assertEquals(1, foundProjects.size());
        assertEquals(DTPTechnology.AFTER_EFFECTS, foundProjects.get(0).getDtpTechnology());
    }

    @Test
    void findByPagesGreaterThan() {
        DTPProject dtpProject1 = new DTPProject("Project 1", "Description 1", null, null, BigDecimal.ONE, null, DTPTechnology.INDESIGN, 200);
        DTPProject dtpProject2 = new DTPProject("Project 2", "Description 2", null, null, BigDecimal.TEN, null, DTPTechnology.INDESIGN, 300);
        dtpProjectRepository.save(dtpProject1);
        dtpProjectRepository.save(dtpProject2);

        List<DTPProject> foundProjects = dtpProjectRepository.findByPagesGreaterThan(250);

        assertEquals(1, foundProjects.size());
        assertEquals("Project 2", foundProjects.get(0).getName());
    }

    @Test
    void findByPagesLessThan() {
        DTPProject dtpProject1 = new DTPProject("Project 1", "Description 1", null, null, BigDecimal.ONE, null, DTPTechnology.INDESIGN, 200);
        DTPProject dtpProject2 = new DTPProject("Project 2", "Description 2", null, null, BigDecimal.TEN, null, DTPTechnology.INDESIGN, 300);
        dtpProjectRepository.save(dtpProject1);
        dtpProjectRepository.save(dtpProject2);

        List<DTPProject> foundProjects = dtpProjectRepository.findByPagesLessThan(250);

        assertEquals(2, foundProjects.size());
        assertEquals("Project 1", foundProjects.get(0).getName());
    }
}
