package com.ironhack.locmgmt.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/*NOT WORKING WITH SECURITY ON*/

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAllAdmins() throws Exception {
        mockMvc.perform(get("/admins/get")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAdminById() throws Exception {
        Long adminId = 1L;  // Replace with a valid existing admin ID
        mockMvc.perform(get("/admins/get/" + adminId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateAdmin() throws Exception {
        Long adminId = 1L;  // Replace with a valid existing admin ID
        String updateRequest = "{\"/* Specify update fields and values */\"}";
        mockMvc.perform(put("/admins/update/" + adminId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateRequest))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteAdmin() throws Exception {
        Long adminId = 1L;  // Replace with a valid existing admin ID
        mockMvc.perform(delete("/admins/delete/" + adminId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetAdminsByDepartment() throws Exception {
        String department = "SALES";  // Replace with a valid department value
        mockMvc.perform(get("/admins/get/byDepartment")
                        .param("department", department)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}