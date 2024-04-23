package com.ironhack.locmgmt.model.users;

import com.ironhack.locmgmt.model.enums.Languages;
import com.ironhack.locmgmt.model.enums.ProjectType;
import lombok.*;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "project_managers")
public class ProjectManager extends User {
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Languages> languages;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<ProjectType> projectTypes;
}

