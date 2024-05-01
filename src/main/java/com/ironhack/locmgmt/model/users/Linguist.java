package com.ironhack.locmgmt.model.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ironhack.locmgmt.model.Rate;
import com.ironhack.locmgmt.model.enums.*;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import jakarta.persistence.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Linguist extends User {
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private UserType userType = UserType.LINGUIST;

    @NotNull(message = "Source languages cannot be empty")
    @Enumerated(EnumType.STRING)
    private List<Languages> sourceLanguages;

    @NotNull(message = "Target languages cannot be empty")
    @Enumerated(EnumType.STRING)
    private List<Languages> targetLanguages;

    @NotNull(message = "Project types cannot be empty")
    @Enumerated(EnumType.STRING)
    private List<ProjectType> projectTypes;

    @NotNull(message = "DTP technologies cannot be empty")
    @Enumerated(EnumType.STRING)
    private List<DTPTechnology> dtpTechnologies;

    @NotNull(message = "Linguistic technologies cannot be empty")
    @Enumerated(EnumType.STRING)
    private List<LinguisticTechnology> linguisticTechnologies;

    @OneToMany(mappedBy = "linguist")
    @JsonIgnoreProperties("linguist")
    private List<Rate> rates;

    /*//Constructor for testing
    public Linguist(String username, String password, String name, String email, UserType userType, List<Languages> languages, List<ProjectType> projectTypes, List<DTPTechnology> dtpTechnologies, List<LinguisticTechnology> linguisticTechnologies) {
        super(username, password, name, email, userType);
        this.languages = languages;
        this.projectTypes = projectTypes;
        this.dtpTechnologies = dtpTechnologies;
        this.linguisticTechnologies = linguisticTechnologies;
    }*/
}