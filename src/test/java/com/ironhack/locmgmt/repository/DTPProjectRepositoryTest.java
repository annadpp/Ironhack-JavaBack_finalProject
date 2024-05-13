package com.ironhack.locmgmt.repository;

import com.ironhack.locmgmt.model.enums.DTPTechnology;
import com.ironhack.locmgmt.model.enums.ProjectType;
import com.ironhack.locmgmt.model.projects.DTPProject;
import com.ironhack.locmgmt.repository.DTPProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DTPProjectRepositoryTest {

    @Autowired
    private DTPProjectRepository dtpProjectRepository;

    @Test
    public void findByDtpTechnology_WhenValidDtpTechnology_ReturnsMatchingProjects() {
        DTPProject project1 = new DTPProject("Project 1", "Description 1", null, null, BigDecimal.TEN, null, DTPTechnology.FIGMA);
        DTPProject project2 = new DTPProject("Project 2", "Description 2", null, null, BigDecimal.TEN, null, DTPTechnology.FIGMA);
        dtpProjectRepository.saveAll(List.of(project1, project2));

        List<DTPProject> projects = dtpProjectRepository.findByDtpTechnology(DTPTechnology.FIGMA);

        assertEquals(2, projects.size());
        assertTrue(projects.contains(project1));
    }

    @Test
    public void findByName_WhenInvalidName_ReturnsEmptyOptional() {
        Optional<DTPProject> optionalProject = dtpProjectRepository.findByName("Nonexistent Project");

        assertTrue(optionalProject.isEmpty());
    }
}
