package com.ironhack.locmgmt.model.projects;

import com.ironhack.locmgmt.model.enums.DTPTechnology;

import lombok.*;
import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dtp_projects")
public class DTPProject extends Project {
    @Enumerated(EnumType.STRING)
    private DTPTechnology dtpTechnology;

    private Integer pages;
}

