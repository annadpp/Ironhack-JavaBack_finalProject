package com.ironhack.locmgmt.repository;

import com.ironhack.locmgmt.model.enums.Languages;
import com.ironhack.locmgmt.model.enums.ProjectType;
import com.ironhack.locmgmt.model.users.ProjectManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ProjectManagerRepositoryTest {

    @Autowired
    private ProjectManagerRepository projectManagerRepository;

    @Test
    void testFindBySpokenLanguages() {
        List<ProjectManager> projectManagers = projectManagerRepository.findBySpokenLanguages(Languages.ENGLISH);

        assertEquals(0, projectManagers.size());
    }

    @Test
    void testFindByProjectTypes() {
        List<ProjectManager> projectManagers = projectManagerRepository.findByProjectTypes(ProjectType.TRANSLATION);

        assertEquals(0, projectManagers.size());
    }
}
