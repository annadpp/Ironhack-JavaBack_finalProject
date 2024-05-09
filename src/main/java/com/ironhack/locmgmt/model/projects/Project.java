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
import java.util.ArrayList;
import java.util.Collections;
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

    //GETS SOURCE LANGUAGE FROM TASKS ASSIGNED TO THE PROJECT
    @Transient
    public Languages getSourceLanguage() {
        if (tasks == null || tasks.isEmpty()) {
            return null;
        }
        return tasks.get(0).getSourceLanguage();
    }

    //GETS TARGET LANGUAGES FROM TASKS ASSIGNED TO THE PROJECT
    @Transient
    public List<Languages> getTargetLanguages() {
        if (tasks == null || tasks.isEmpty()) {
            return Collections.emptyList();
        }

        List<Languages> targetLanguages = new ArrayList<>();
        for (Task task : tasks) {
            targetLanguages.add(task.getTargetLanguage());
        }
        return targetLanguages;
    }

    @ManyToOne
    @JoinColumn(name = "project_manager_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_rate_linguist", foreignKeyDefinition = "FOREIGN KEY (linguist_id) REFERENCES users (id) ON DELETE SET NULL"))
    @JsonIgnoreProperties({"projects", "password", "tasks"})
    private ProjectManager projectManager;

    //GETS LINGUISTS FROM TASKS ASSIGNED TO THE PROJECT
    @Transient
    @JsonIgnoreProperties({"projects", "rates", "tasks", "sourceLanguages", "targetLanguages", "projectTypes", "dtpTechnologies", "linguisticTechnologies"})
    public List<Linguist> getLinguists() {
        if (tasks == null || tasks.isEmpty()) {
            return Collections.emptyList();
        }

        List<Linguist> linguists = new ArrayList<>();
        for (Task task : tasks) {
            linguists.add(task.getLinguist());
        }
        return linguists;
    }

    @OneToMany(mappedBy = "project", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JsonIgnoreProperties({"project", "projectManager", "linguist"})
    private List<Task> tasks;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_rate_linguist", foreignKeyDefinition = "FOREIGN KEY (linguist_id) REFERENCES users (id) ON DELETE SET NULL"))
    @JsonIgnoreProperties("projects")
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

