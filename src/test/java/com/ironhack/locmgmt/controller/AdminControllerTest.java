package com.ironhack.locmgmt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.locmgmt.model.enums.Department;
import com.ironhack.locmgmt.model.enums.UserType;
import com.ironhack.locmgmt.model.users.Admin;
import com.ironhack.locmgmt.service.AdminService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AdminService adminService;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Admin admin = new Admin("John", "password", "John Doe", "john@example.com", UserType.ADMIN, Department.IT);
        adminService.createAdmin(admin);
    }

    @AfterEach
    void tearDown() {
        adminService.getAllAdmins().forEach(admin -> adminService.deleteAdmin(admin.getId()));
    }

    @Test
    void getAllClients_ValidRequest_ReturnsAdmins() throws Exception {
        mockMvc.perform(get("/admins/get"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("John Doe")));
    }

    @Test
    void getAdminById_ExistingId_ReturnsAdmin() throws Exception {
        // Perform the request to get the admin by ID
        MvcResult result = mockMvc.perform(get("/admins/get/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Extract the response body as a string
        String responseBody = result.getResponse().getContentAsString();

        // Parse the JSON response into an Admin object
        Admin admin = objectMapper.readValue(responseBody, Admin.class);

        // Validate that the returned admin has the correct name
        assertEquals("John Doe", admin.getName());
    }

    @Test
    void createAdmin_ValidAdmin_ReturnsCreatedAdmin() throws Exception {
        Admin admin = new Admin("Jane", "password", "Jane Smith", "jane@example.com", UserType.ADMIN, Department.ADMINISTRATION);
        String body = objectMapper.writeValueAsString(admin);
        mockMvc.perform(post("/admins/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Jane Smith")));
    }

    @Test
    void updateAdmin_ExistingIdAndValidAdmin_ReturnsUpdatedAdmin() throws Exception {
        // Create an updated Admin object with the desired changes
        Admin updatedAdmin = new Admin();
        updatedAdmin.setName("Jane Smith");
        updatedAdmin.setEmail("jane.smith@example.com");
        updatedAdmin.setPassword("newPassword");
        updatedAdmin.setDepartment(Department.ADMINISTRATION);

        // Convert the updated Admin object to JSON
        String updatedAdminJson = objectMapper.writeValueAsString(updatedAdmin);

        // Perform the update operation by calling the controller endpoint with the updated JSON payload
        mockMvc.perform(put("/admins/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedAdminJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jane Smith")) // Validate the updated name
                .andExpect(jsonPath("$.email").value("jane.smith@example.com")) // Validate the updated email
                .andExpect(jsonPath("$.department").value("ADMINISTRATION")); // Validate the updated department
    }

    @Test
    void deleteAdmin_ExistingId_ReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/admins/delete/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getAdminsByDepartment_ValidDepartment_ReturnsAdmins() throws Exception {
        mockMvc.perform(get("/admins/get/byDepartment")
                        .param("department", Department.IT.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("John Doe")));
    }
}

