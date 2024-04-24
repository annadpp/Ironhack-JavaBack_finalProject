package com.ironhack.locmgmt.model.users;

import com.ironhack.locmgmt.model.Rate;
import com.ironhack.locmgmt.model.enums.*;

import lombok.*;
import jakarta.persistence.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "linguists")
public class Linguist extends User {
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Languages> languages;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<ProjectType> projectTypes;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<DTPTechnology> dtpTechnologies;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<LinguisticTechnology> linguisticTechnologies;

    @OneToMany(mappedBy = "linguist")
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