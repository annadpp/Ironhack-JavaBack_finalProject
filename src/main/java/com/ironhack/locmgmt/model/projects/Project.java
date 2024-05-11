package com.ironhack.locmgmt.model.projects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ironhack.locmgmt.model.Client;
import com.ironhack.locmgmt.model.Task;
import com.ironhack.locmgmt.model.users.Linguist;
import com.ironhack.locmgmt.model.users.ProjectManager;
import com.ironhack.locmgmt.model.enums.Languages;
import com.ironhack.locmgmt.model.enums.ProjectType;
import com.ironhack.locmgmt.model.enums.Status;

import jakarta.validation.constraints.*;
import lombok.*;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

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

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    private String description;

    //Set with date when projectStatus == started
    private Date startDate;

    //Set with date when projectStatus == finished
    private Date endDate;

    @Future(message = "The 'deadline' field must be a future date in the format 'yyyy-MM-dd'")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deadline;

    //Set with difference between startDate and deadline
    private Duration timeRemaining;

    @NotNull(message = "Budget cannot be empty")
    @Positive(message = "Budget must be positive")
    private BigDecimal totalBudget;

    //Sets by adding all costs from tasks and getting the difference to totalBudget
    private BigDecimal margin;

    //Sets by dividing margin / totalBudget * 100
    private BigDecimal marginPercentage;

    @Enumerated(EnumType.STRING)
    private Status projectStatus;

    @Enumerated(EnumType.STRING)
    private ProjectType projectType;

    //Gets source language from tasks assigned to the project
    @Transient
    public Languages getSourceLanguage() {
        if (tasks == null || tasks.isEmpty()) {
            return null;
        }
        return tasks.get(0).getSourceLanguage();
    }

    //Gets target languages from tasks assigned to the project -> Set so no duplicate records
    @Transient
    public Set<Languages> getTargetLanguages() {
        if (tasks == null || tasks.isEmpty()) {
            return Collections.emptySet();
        }

        Set<Languages> targetLanguages = new HashSet<>();
        for (Task task : tasks) {
            targetLanguages.add(task.getTargetLanguage());
        }
        return targetLanguages;
    }

    @ManyToOne
    @JoinColumn(name = "project_manager_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_rate_linguist", foreignKeyDefinition = "FOREIGN KEY (linguist_id) REFERENCES users (id) ON DELETE SET NULL"))
    @JsonIgnoreProperties({"projects", "password", "tasks", "role", "spokenLanguages", "projectTypes", "enabled", "accountNonExpired", "accountNonLocked", "credentialsNonExpired", "authorities"})
    private ProjectManager projectManager;

    //Gets linguists from tasks assigned to the project -> Set so there are no duplicate records
    @Transient
    @JsonIgnoreProperties({"password", "projects", "rates", "tasks", "sourceLanguages", "targetLanguages", "projectTypes", "dtpTechnologies", "linguisticTechnologies", "role", "enabled", "accountNonLocked", "accountNonExpired", "credentialsNonExpired", "authorities"})
    public Set<Linguist> getLinguists() {
        if (tasks == null || tasks.isEmpty()) {
            return Collections.emptySet();
        }

        Set<Linguist> linguists = new HashSet<>();
        for (Task task : tasks) {
            linguists.add(task.getLinguist());
        }
        return linguists;
    }

    @OneToMany(mappedBy = "project", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JsonIgnoreProperties({"project", "projectManager", "linguist", "timeRemaining", "totalTime", "startDate", "endDate", "billingStatus", "newWords", "fuzzyWords"})
    private List<Task> tasks;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_rate_linguist", foreignKeyDefinition = "FOREIGN KEY (linguist_id) REFERENCES users (id) ON DELETE SET NULL"))
    @JsonIgnoreProperties({"projects", "address", "vatnumber"})
    private Client client;

    //Constructor/getters/setters for testing
    public Project(String name, String description, Date startDate, Date endDate, BigDecimal totalBudget, Status projectStatus, ProjectType projectType) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalBudget = totalBudget;
        this.projectStatus = projectStatus;
        this.projectType = projectType;
    }
}

