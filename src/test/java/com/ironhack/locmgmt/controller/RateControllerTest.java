package com.ironhack.locmgmt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.locmgmt.model.Rate;
import com.ironhack.locmgmt.model.enums.Languages;
import com.ironhack.locmgmt.model.enums.ProjectType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Rate rate;

    @BeforeEach
    public void setUp() {
        rate = new Rate(BigDecimal.valueOf(0.05), Languages.ENGLISH, Languages.SPANISH, ProjectType.TRANSLATION);
    }

    @Test
    public void createRate_ValidRate_ReturnsCreatedRate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/rates/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rate)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.wordPrice").value("0.05"))
                .andExpect(jsonPath("$.sourceLanguage").value("ENGLISH"))
                .andExpect(jsonPath("$.targetLanguage").value("SPANISH"))
                .andExpect(jsonPath("$.projectType").value("TRANSLATION"));
    }

    @Test
    public void updateRate_ExistingIdAndValidRate_ReturnsUpdatedRate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/rates/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rate)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.wordPrice").value("0.05"))
                .andExpect(jsonPath("$.sourceLanguage").value("ENGLISH"))
                .andExpect(jsonPath("$.targetLanguage").value("SPANISH"))
                .andExpect(jsonPath("$.projectType").value("TRANSLATION"));
    }

    @Test
    public void getRateById_ExistingId_ReturnsRate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rates/get/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.wordPrice").value("0.05"))
                .andExpect(jsonPath("$.sourceLanguage").value("ENGLISH"))
                .andExpect(jsonPath("$.targetLanguage").value("SPANISH"))
                .andExpect(jsonPath("$.projectType").value("TRANSLATION"));
    }
}