package com.ironhack.locmgmt.model.projects;

import com.ironhack.locmgmt.model.enums.DTPTechnology;
import com.ironhack.locmgmt.model.enums.ProjectType;
import com.ironhack.locmgmt.model.enums.Status;

import com.ironhack.locmgmt.validation.annotations.ValidProjectManager;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ValidProjectManager //More info in validators
public class DTPProject extends Project {
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private ProjectType projectType = ProjectType.DTP;

    @NotNull(message = "DTP technology cannot be empty")
    @Enumerated(EnumType.STRING)
    private DTPTechnology dtpTechnology;

    //Constructor for testing
    public DTPProject(String name, String description, Date startDate, Date endDate, BigDecimal totalBudget, Status projectStatus, DTPTechnology dtpTechnology) {
        super(name, description, startDate, endDate, totalBudget, projectStatus, ProjectType.DTP);
        this.dtpTechnology = dtpTechnology;
    }
}

