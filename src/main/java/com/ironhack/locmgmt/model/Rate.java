package com.ironhack.locmgmt.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ironhack.locmgmt.model.enums.Languages;
import com.ironhack.locmgmt.model.enums.ProjectType;

import com.ironhack.locmgmt.model.users.Linguist;

import com.ironhack.locmgmt.validation.annotations.ValidRate;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ValidRate
@Table(name = "rates")
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Positive(message = "Word price must be positive.")
    private BigDecimal wordPrice;

    @Positive(message = "Page price must be positive.")
    private BigDecimal pagePrice;

    @NotNull(message = "Source language cannot be empty")
    @Enumerated(EnumType.STRING)
    private Languages sourceLanguage;

    @NotNull(message = "Target language cannot be empty")
    @Enumerated(EnumType.STRING)
    private Languages targetLanguage;

    @NotNull(message = "Project type cannot be empty")
    @Enumerated(EnumType.STRING)
    private ProjectType projectType;

    @ManyToOne
    @JoinColumn(name = "linguist_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_rate_linguist", foreignKeyDefinition = "FOREIGN KEY (linguist_id) REFERENCES users (id) ON DELETE SET NULL"))
    @JsonIgnoreProperties({"rates", "password", "userType", "tasks", "projects", "sourceLanguages", "targetLanguages", "projectTypes", "dtpTechnologies", "linguisticTechnologies"})
    @JsonBackReference
    private Linguist linguist;

    //Constructor for testing
    public Rate(BigDecimal wordPrice, Languages sourceLanguage, Languages targetLanguage, ProjectType projectType) {
        this.wordPrice = wordPrice;
        this.sourceLanguage = sourceLanguage;
        this.targetLanguage = targetLanguage;
        this.projectType = projectType;
    }
}

