package com.ironhack.locmgmt.model.projects;

import com.ironhack.locmgmt.model.enums.Languages;
import com.ironhack.locmgmt.model.enums.LinguisticTechnology;

import lombok.*;
import jakarta.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "linguistic_projects")
public class LinguisticProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer newWords;

    private Integer fuzzyWords;

    @Enumerated(EnumType.STRING)
    private LinguisticTechnology linguisticTechnology;

    @Enumerated(EnumType.STRING)
    private Languages sourceLanguage;

    @OneToMany(mappedBy = "project")
    private List<Languages> targetLanguages;
}
