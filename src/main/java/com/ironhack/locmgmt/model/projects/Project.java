package com.ironhack.locmgmt.model.projects;

import com.ironhack.locmgmt.model.Client;
import com.ironhack.locmgmt.model.Task;
import com.ironhack.locmgmt.model.enums.ProjectType;
import com.ironhack.locmgmt.model.enums.TaskStatus;
import com.ironhack.locmgmt.model.users.User;

import lombok.*;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String name;

    private String description;

    private Date startDate;

    private Date endDate;

    private BigDecimal totalBudget;

    @Enumerated(EnumType.STRING)
    private TaskStatus projectStatus;

    @Enumerated(EnumType.STRING)
    private ProjectType projectType;

    @ManyToMany
    @JoinTable(
            name = "project_user",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;

    @OneToMany(mappedBy = "project")
    private List<Task> tasks;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
}

