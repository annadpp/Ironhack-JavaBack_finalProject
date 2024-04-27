package com.ironhack.locmgmt.model;

import com.ironhack.locmgmt.model.enums.Languages;
import com.ironhack.locmgmt.model.enums.ProjectType;

import com.ironhack.locmgmt.model.users.Linguist;

import lombok.*;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rates")
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private Languages sourceLanguage;

    @Enumerated(EnumType.STRING)
    private Languages targetLanguage;

    @Enumerated(EnumType.STRING)
    private ProjectType projectType;

    @ManyToOne
    @JoinColumn(name = "linguist_id")
    private Linguist linguist;


    /*//Constructor for testing
    public Rate(BigDecimal price, Languages language, ProjectType projectType) {
        this.price = price;
        this.language = language;
        this.projectType = projectType;
    }*/
}

