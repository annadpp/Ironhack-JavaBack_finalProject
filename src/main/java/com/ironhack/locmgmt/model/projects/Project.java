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

/*@Data
@AllArgsConstructor*/
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

    public Project(String name, String description, Date startDate, Date endDate, BigDecimal totalBudget, TaskStatus projectStatus, ProjectType projectType) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalBudget = totalBudget;
        this.projectStatus = projectStatus;
        this.projectType = projectType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(BigDecimal totalBudget) {
        this.totalBudget = totalBudget;
    }

    public TaskStatus getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(TaskStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    public ProjectType getProjectType() {
        return projectType;
    }

    public void setProjectType(ProjectType projectType) {
        this.projectType = projectType;
    }
}

