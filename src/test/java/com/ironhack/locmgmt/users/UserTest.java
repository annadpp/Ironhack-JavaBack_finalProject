/*
package com.ironhack.locmgmt.users;

<<<<<<< HEAD
=======
import com.ironhack.locmgmt.model.projects.Project;
>>>>>>> 20467fa389b2adb55318426690a3b141e388b873
import com.ironhack.locmgmt.model.users.User;
import com.ironhack.locmgmt.model.enums.UserType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserTest {
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setUserType(UserType.ADMIN);
    }

    @Test
    void createUser() {
        assertNotNull(user);
        assertEquals("testUser", user.getUsername());
    }

    @Test
    void testTasksRelation() {
        Task task1 = new Task();
        Task task2 = new Task();
        List<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);

        user.setTasks(tasks);

        assertEquals(2, user.getTasks().size());
    }

    @Test
    void testProjectsRelation() {
        Project project1 = new Project();
        Project project2 = new Project();
        List<Project> projects = new ArrayList<>();
        projects.add(project1);
        projects.add(project2);

        user.setProjects(projects);

        assertEquals(2, user.getProjects().size());
    }
}

*/
