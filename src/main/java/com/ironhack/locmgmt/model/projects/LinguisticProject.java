package com.ironhack.locmgmt.model.projects;

import com.ironhack.locmgmt.model.enums.LinguisticTechnology;
import com.ironhack.locmgmt.model.enums.ProjectType;

import com.ironhack.locmgmt.model.enums.Status;
import com.ironhack.locmgmt.validation.annotations.ValidLinguisticTechnology;
import jakarta.validation.constraints.Positive;
import lombok.*;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LinguisticProject extends Project {
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private ProjectType projectType = ProjectType.LINGUISTIC;

    /*@Positive(message = "New words must be positive")
    private Integer newWords;

    @Positive(message = "Fuzzy words must be positive")
    private Integer fuzzyWords;

    private Integer totalWords;*/

    @Enumerated(EnumType.STRING)
    private LinguisticTechnology linguisticTechnology;


    //Constructor for testing
    public LinguisticProject(String name, String description, Date startDate, Date endDate, BigDecimal totalBudget, Status projectStatus, Integer newWords, Integer fuzzyWords, LinguisticTechnology linguisticTechnology) {
        super(name, description, startDate, endDate, totalBudget, projectStatus, ProjectType.LINGUISTIC);
/*        this.newWords = newWords;
        this.fuzzyWords = fuzzyWords;*/
        this.linguisticTechnology = linguisticTechnology;
    }
}
