package com.ironhack.locmgmt.model.users;

import com.ironhack.locmgmt.model.Rate;
import com.ironhack.locmgmt.model.enums.DTPTechnology;
import com.ironhack.locmgmt.model.enums.Languages;
import com.ironhack.locmgmt.model.enums.LinguisticTechnology;
import com.ironhack.locmgmt.model.enums.ProjectType;

import lombok.*;
import jakarta.persistence.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "linguists")
public class Linguist extends User {
    @OneToMany(mappedBy = "linguist")
    private List<Languages> languages;

    @OneToMany(mappedBy = "linguist")
    private List<ProjectType> projectTypes;

    @OneToMany(mappedBy = "linguist")
    private List<DTPTechnology> dtpTechnologies;

    @OneToMany(mappedBy = "linguist")
    private List<LinguisticTechnology> linguisticTechnologies;

    @OneToMany(mappedBy = "linguist")
    private List<Rate> rates;
}
