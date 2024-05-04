package com.ironhack.locmgmt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.locmgmt.model.enums.Department;
import com.ironhack.locmgmt.model.enums.UserType;
import com.ironhack.locmgmt.model.users.Admin;
import com.ironhack.locmgmt.model.users.UserEntity;
import com.ironhack.locmgmt.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class UserEntityControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        UserEntity userEntity = new UserEntity("john", "password", "John Doe", "john@example.com", UserType.ADMIN);
        userRepository.save(userEntity);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void getUserById_ExistingId_ReturnsUser() throws Exception {
        MvcResult result = mockMvc.perform(get("/users/get/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        UserEntity userEntity = objectMapper.readValue(responseBody, UserEntity.class);

        assertEquals("John Doe", userEntity.getName());
    }

    @Test
    void deleteUser_ExistingId_UserDeleted() throws Exception {
        mockMvc.perform(delete("/users/delete/1"))
                .andExpect(status().isNoContent());

        assertFalse(userRepository.existsById(1L));
    }

    @Test
    void getUserById_NonExistingId_ReturnsNotFound() throws Exception {
        mockMvc.perform(get("/users/get/100"))
                .andExpect(status().isNotFound());
    }

    //
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

}

