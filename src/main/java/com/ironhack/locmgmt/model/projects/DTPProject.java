package com.ironhack.locmgmt.model.projects;

import com.ironhack.locmgmt.model.enums.DTPTechnology;

import com.ironhack.locmgmt.model.enums.ProjectType;
import com.ironhack.locmgmt.model.enums.TaskStatus;
import lombok.*;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
public class DTPProject extends Project {
    @Enumerated(EnumType.STRING)
    private DTPTechnology dtpTechnology;

    private Integer pages;

    /*//Constructor for testing
    public DTPProject(String name, String description, Date startDate, Date endDate, BigDecimal totalBudget, TaskStatus projectStatus, ProjectType projectType, DTPTechnology dtpTechnology, Integer pages) {
        super(name, description, startDate, endDate, totalBudget, projectStatus, projectType);
        this.dtpTechnology = dtpTechnology;
        this.pages = pages;
    }*/
}

