package com.ironhack.locmgmt.model.projects;

import com.ironhack.locmgmt.model.enums.LinguisticTechnology;
import com.ironhack.locmgmt.model.enums.ProjectType;
import com.ironhack.locmgmt.model.enums.Status;

import com.ironhack.locmgmt.validation.annotations.ValidProjectManager;
import lombok.*;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ValidProjectManager //More info in validators
public class LinguisticProject extends Project {
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private ProjectType projectType = ProjectType.LINGUISTIC;

    @Enumerated(EnumType.STRING)
    private LinguisticTechnology linguisticTechnology;

    //Constructor for testing
    public LinguisticProject(String name, String description, Date startDate, Date endDate, BigDecimal totalBudget, Status projectStatus, LinguisticTechnology linguisticTechnology) {
        super(name, description, startDate, endDate, totalBudget, projectStatus, ProjectType.LINGUISTIC);
        this.linguisticTechnology = linguisticTechnology;
    }
}
