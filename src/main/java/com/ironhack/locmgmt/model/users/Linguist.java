package com.ironhack.locmgmt.model.users;

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

    @NotNull
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Languages> sourceLanguages;

    @NotNull
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Languages> targetLanguages;

    @NotNull
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<ProjectType> projectTypes;

    @NotNull
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<DTPTechnology> dtpTechnologies;

    @NotNull
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<LinguisticTechnology> linguisticTechnologies;

    /*@OneToMany(mappedBy = "linguist")
    private List<Rate> rates;*/

    /*//Constructor for testing
    public Linguist(String username, String password, String name, String email, UserType userType, List<Languages> languages, List<ProjectType> projectTypes, List<DTPTechnology> dtpTechnologies, List<LinguisticTechnology> linguisticTechnologies) {
        super(username, password, name, email, userType);
        this.languages = languages;
        this.projectTypes = projectTypes;
        this.dtpTechnologies = dtpTechnologies;
        this.linguisticTechnologies = linguisticTechnologies;
    }*/
}
