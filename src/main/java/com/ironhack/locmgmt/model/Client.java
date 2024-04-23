package com.ironhack.locmgmt.model;

import com.ironhack.locmgmt.model.projects.Project;

import lombok.*;
import jakarta.persistence.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String name;

    private String email;

    private String VATNumber;

    private String address;

    @OneToMany(mappedBy = "client")
    private List<Project> projects;
}
