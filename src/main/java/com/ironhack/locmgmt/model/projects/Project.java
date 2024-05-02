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

    /*ADD MARGIN -> CALCULATE WITH TASKS PRICE*/

    @Enumerated(EnumType.STRING)
    private Status projectStatus;

    @Enumerated(EnumType.STRING)
    private ProjectType projectType;

    /*fix taking from tasks assigned to this project*/
    /*@Enumerated(EnumType.STRING)
    private Languages sourceLanguage;

    @Enumerated(EnumType.STRING)
    private List<Languages> targetLanguages;*/

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
    @JoinColumn(name = "project_manager_id")
    @JsonIgnoreProperties({"projects", "password", "tasks"})
    private ProjectManager projectManager;

    //gets linguists from tasks assigned to this project
    /*@ManyToMany
    @JoinTable(
            name = "project_linguists",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "linguist_id")
    )
    private List<Linguist> linguists;*/

    //GETS LINGUISTS FROM TASKS ASSIGNED TO THE PROJECT
    @Transient
    @JsonIgnoreProperties({"projects", "rates", "tasks"})
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


    @OneToMany(mappedBy = "project")
    @JsonIgnoreProperties({"project", "projectManager", "linguist"})
    private List<Task> tasks;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonIgnoreProperties("projects")
    private Client client;

    /*//Constructor/getters/setters for testing
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
    }*/
}

