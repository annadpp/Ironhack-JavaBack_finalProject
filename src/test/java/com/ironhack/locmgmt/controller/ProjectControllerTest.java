package com.ironhack.locmgmt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.locmgmt.model.projects.Project;
import com.ironhack.locmgmt.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProjectService projectService;

    /*DOESN'T WORK WITH SECURITY ENABLED*/
    @Test
    void getAllProjects_Success() throws Exception {
        //Perform GET request to fetch all projects
        mockMvc.perform(MockMvcRequestBuilders.get("/projects/get"))
                .andExpect(status().isOk()) // Expect HTTP status 200
                .andExpect(jsonPath("$").isArray()); // Expect response to be an array
    }

    @Test
    void getProjectById_Success() throws Exception {
        // Create a project
        Project project = new Project();
        project.setName("Project A");
        project.setTotalBudget(new BigDecimal("10000.00"));
        //Save the project
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/projects/create")
                        .content(asJsonString(project))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        //Extract project id from the response
        String responseBody = result.getResponse().getContentAsString();
        Long projectId = Long.valueOf(responseBody.split(":")[1].split(",")[0]);

        //Perform GET request to fetch the project by id
        mockMvc.perform(MockMvcRequestBuilders.get("/projects/get/{id}", projectId))
                .andExpect(status().isOk()) // Expect HTTP status 200
                .andExpect(jsonPath("$.name").value(project.getName())) // Expect the correct name in the response
                .andExpect(jsonPath("$.totalBudget").value(project.getTotalBudget())); // Expect the correct total budget in the response
    }

    @Test
    void deleteProject_Success() throws Exception {
        //Create a project
        Project project = new Project();
        project.setName("Project B");
        project.setTotalBudget(new BigDecimal("20000.00"));
        //Save the project
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/projects/create")
                        .content(asJsonString(project))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        //Extract project id from the response
        String responseBody = result.getResponse().getContentAsString();
        Long projectId = Long.valueOf(responseBody.split(":")[1].split(",")[0]);

        //Perform DELETE request to delete the project
        mockMvc.perform(MockMvcRequestBuilders.delete("/projects/delete/{id}", projectId))
                .andExpect(status().isNoContent()); // Expect HTTP status 204
    }

    //Helper method to convert object to JSON string
    private String asJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
