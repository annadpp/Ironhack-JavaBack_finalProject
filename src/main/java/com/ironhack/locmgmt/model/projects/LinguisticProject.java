package com.ironhack.locmgmt.model.projects;

import com.ironhack.locmgmt.model.enums.Languages;
import com.ironhack.locmgmt.model.enums.LinguisticTechnology;

import com.ironhack.locmgmt.model.enums.ProjectType;
import com.ironhack.locmgmt.model.enums.TaskStatus;
import lombok.*;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LinguisticProject extends Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer newWords;

    private Integer fuzzyWords;

    @Enumerated(EnumType.STRING)
    private LinguisticTechnology linguisticTechnology;

    @Enumerated(EnumType.STRING)
    private Languages sourceLanguage;

    @ElementCollection(targetClass = Languages.class)
    @CollectionTable(
            name = "project_languages",
            joinColumns = @JoinColumn(name = "project_id")
    )
    @Column(name = "language")
    private List<Languages> targetLanguages;

    /*//Constructor for testing
    public LinguisticProject(String name, String description, Date startDate, Date endDate, BigDecimal totalBudget, TaskStatus projectStatus, ProjectType projectType, Integer newWords, Integer fuzzyWords, LinguisticTechnology linguisticTechnology, Languages sourceLanguage, List<Languages> targetLanguages) {
        super(name, description, startDate, endDate, totalBudget, projectStatus, projectType);
        this.newWords = newWords;
        this.fuzzyWords = fuzzyWords;
        this.linguisticTechnology = linguisticTechnology;
        this.sourceLanguage = sourceLanguage;
        this.targetLanguages = targetLanguages;
    }*/
}
