package com.ironhack.locmgmt.repository;

import com.ironhack.locmgmt.model.enums.LinguisticTechnology;
import com.ironhack.locmgmt.model.enums.Status;
import com.ironhack.locmgmt.model.projects.LinguisticProject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class LinguisticProjectRepositoryTest {

    @Autowired
    private LinguisticProjectRepository linguisticProjectRepository;

    @Test
    void testFindByNewWordsLessThanAndFuzzyWordsLessThan() {
        LinguisticProject project = new LinguisticProject("Project 1", "Description", new Date(), new Date(), BigDecimal.valueOf(1000), Status.STARTED, 500, 300, LinguisticTechnology.EMAIL);
        linguisticProjectRepository.save(project);

        List<LinguisticProject> foundProjects = linguisticProjectRepository.findByNewWordsLessThanAndFuzzyWordsLessThan(600, 400);

        assertEquals(1, foundProjects.size());
        assertTrue(foundProjects.contains(project));
    }

    @Test
    void testFindByNewWordsGreaterThanAndFuzzyWordsGreaterThan() {
        LinguisticProject project = new LinguisticProject("Project 2", "Description", new Date(), new Date(), BigDecimal.valueOf(1500), Status.STARTED, 700, 500, LinguisticTechnology.EMAIL);
        linguisticProjectRepository.save(project);

        List<LinguisticProject> foundProjects = linguisticProjectRepository.findByNewWordsGreaterThanAndFuzzyWordsGreaterThan(600, 400);

        assertEquals(1, foundProjects.size());
        assertTrue(foundProjects.contains(project));
    }

    @Test
    void testFindByTotalWordsGreaterThan() {
        LinguisticProject project = new LinguisticProject("Project 3", "Description", new Date(), new Date(), BigDecimal.valueOf(2000), Status.STARTED, 1000, 500, LinguisticTechnology.EMAIL);
        linguisticProjectRepository.save(project);

        List<LinguisticProject> foundProjects = linguisticProjectRepository.findByTotalWordsGreaterThan(800);

        assertEquals(1, foundProjects.size());
        assertTrue(foundProjects.contains(project));
    }

    @Test
    void testFindByTotalWordsLessThan() {
        LinguisticProject project = new LinguisticProject("Project 4", "Description", new Date(), new Date(), BigDecimal.valueOf(2500), Status.STARTED, 1500, 1000, LinguisticTechnology.EMAIL);
        linguisticProjectRepository.save(project);

        List<LinguisticProject> foundProjects = linguisticProjectRepository.findByTotalWordsLessThan(3000);

        assertEquals(1, foundProjects.size());
        assertTrue(foundProjects.contains(project));
    }

    @Test
    void testFindByTotalBudgetGreaterThan() {
        LinguisticProject project = new LinguisticProject("Project 5", "Description", new Date(), new Date(), BigDecimal.valueOf(3000), Status.STARTED, 2000, 1500, LinguisticTechnology.EMAIL);
        linguisticProjectRepository.save(project);

        List<LinguisticProject> foundProjects = linguisticProjectRepository.findByTotalBudgetGreaterThan(BigDecimal.valueOf(2500));

        assertEquals(1, foundProjects.size());
        assertTrue(foundProjects.contains(project));
    }

    @Test
    void testFindByTotalBudgetLessThan() {
        LinguisticProject project = new LinguisticProject("Project 6", "Description", new Date(), new Date(), BigDecimal.valueOf(3500), Status.STARTED, 2500, 2000, LinguisticTechnology.EMAIL);
        linguisticProjectRepository.save(project);

        List<LinguisticProject> foundProjects = linguisticProjectRepository.findByTotalBudgetLessThan(BigDecimal.valueOf(4000));

        assertEquals(1, foundProjects.size());
        assertTrue(foundProjects.contains(project));
    }

    @Test
    void testFindByLinguisticTechnology() {
        LinguisticProject project = new LinguisticProject("Project 7", "Description", new Date(), new Date(), BigDecimal.valueOf(4000), Status.STARTED, 3000, 2500, LinguisticTechnology.EMAIL);
        linguisticProjectRepository.save(project);

        List<LinguisticProject> foundProjects = linguisticProjectRepository.findByLinguisticTechnology(LinguisticTechnology.EMAIL);

        assertEquals(1, foundProjects.size());
        assertTrue(foundProjects.contains(project));
    }
}
