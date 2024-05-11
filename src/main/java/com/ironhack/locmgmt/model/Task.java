package com.ironhack.locmgmt.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ironhack.locmgmt.model.enums.*;
import com.ironhack.locmgmt.model.projects.Project;
import com.ironhack.locmgmt.model.users.Linguist;
import com.ironhack.locmgmt.model.users.ProjectManager;

import com.ironhack.locmgmt.util.TaskUtil;
import com.ironhack.locmgmt.validation.annotations.ValidDTPTechnology;
import com.ironhack.locmgmt.validation.annotations.ValidLinguisticTechnology;

import com.ironhack.locmgmt.validation.annotations.ValidTask;
import jakarta.validation.constraints.*;
import lombok.*;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Duration;
import java.util.Date;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "tasks")
@ValidLinguisticTechnology
@ValidDTPTechnology
@ValidTask
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotBlank(message = "Task name cannot be empty")
    private String name;

    private String description;

    @FutureOrPresent(message = "The 'deadline' field must be a date from now in the format 'yyyy-MM-dd'")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deadline;

    //Sets with difference between now and deadline
    private Duration timeRemaining;

    //Sets with difference between startDate and deadline
    private Duration totalTime;

    //Sets automatically when linguist with rates which match task languages + totalWords are assigned
    private BigDecimal taskCost;

    @Enumerated(EnumType.STRING)
    private Status taskStatus;

    @NotNull(message = "Project type cannot be empty")
    @Enumerated(EnumType.STRING)
    private ProjectType projectType;

    //Sets with date when taskStatus == started
    private Date startDate;

    //Sets with date when taskStatus == finished
    private Date endDate;

    @Enumerated(EnumType.STRING)
    private BillingStatus billingStatus;

    @NotNull(message = "Source language cannot be empty")
    @Enumerated(EnumType.STRING)
    private Languages sourceLanguage;

    @NotNull(message = "Target language cannot be empty")
    @Enumerated(EnumType.STRING)
    private Languages targetLanguage;

    @Positive(message = "New words must be positive")
    private Integer newWords;

    @Positive(message = "Fuzzy words must be positive")
    private Integer fuzzyWords;

    //Sets automatically from newWords and fuzzyWords
    private Integer totalWords;

    @Positive(message = "Fuzzy words must be positive")
    private Integer pages;

    //ValidLinguisticTechnology validation depending on projectType
    @Enumerated(EnumType.STRING)
    private LinguisticTechnology linguisticTechnology;

    //ValidDTPTechnology validation depending on projectType
    @Enumerated(EnumType.STRING)
    private DTPTechnology dtpTechnology;

    @ManyToOne
    @JoinColumn(name = "linguist_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_rate_linguist", foreignKeyDefinition = "FOREIGN KEY (linguist_id) REFERENCES users (id) ON DELETE SET NULL"))
    @JsonIgnoreProperties({"tasks", "rates", "password", "userType", "projects", "role", "sourceLanguages", "targetLanguages", "projectTypes", "dtpTechnologies", "linguisticTechnologies", "enabled", "authorities", "accountNonExpired", "accountNonLocked", "credentialsNonExpired"})
    private Linguist linguist;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_rate_linguist", foreignKeyDefinition = "FOREIGN KEY (linguist_id) REFERENCES users (id) ON DELETE SET NULL"))
    @JsonIgnoreProperties({"tasks", "linguists", "client", "projectManager", "timeRemaining", })
    @NotNull(message = "Project cannot be empty")
    private Project project;

    //GETS PROJECT MANAGER FROM PROJECT ASSIGNED TO THE TASK
    @Transient
    @JsonIgnoreProperties({"projects", "tasks", "password", "role", "spokenLanguages", "projectTypes", "role", "enabled", "authorities", "accountNonExpired", "accountNonLocked", "credentialsNonExpired"})
    public ProjectManager getProjectManager() {
        if (project != null) {
            return project.getProjectManager();
        }
        return null;
    }

    //Updates remaining time task is built
    public static TaskBuilder builder() {
        return new CustomTaskBuilder();
    }

    private static class CustomTaskBuilder extends TaskBuilder {
        @Override
        public Task build() {
            Task task = super.build();
            TaskUtil.updateTimeRemaining(task); //Updates remaining time
            return task;
        }
    }

    //Constructor for testing
    public Task(String name, String description, Date deadline, Duration timeRemaining, Status taskStatus, ProjectType projectType, Date startDate, Date endDate, BillingStatus billingStatus, Languages sourceLanguage, Languages targetLanguage, LinguisticTechnology linguisticTechnology, DTPTechnology dtpTechnology, Linguist linguist, Project project) {
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.timeRemaining = timeRemaining;
        this.taskStatus = taskStatus;
        this.projectType = projectType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.billingStatus = billingStatus;
        this.sourceLanguage = sourceLanguage;
        this.targetLanguage = targetLanguage;
        this.linguisticTechnology = linguisticTechnology;
        this.dtpTechnology = dtpTechnology;
        this.linguist = linguist;
        this.project = project;
    }
}
