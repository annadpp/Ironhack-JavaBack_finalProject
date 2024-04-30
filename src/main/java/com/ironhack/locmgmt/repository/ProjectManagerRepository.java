package com.ironhack.locmgmt.repository;

import com.ironhack.locmgmt.model.enums.ProjectType;
import com.ironhack.locmgmt.model.enums.Languages;
import com.ironhack.locmgmt.model.users.ProjectManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectManagerRepository extends JpaRepository<ProjectManager, Long> {
    List<ProjectManager> findBySpokenLanguages(Languages language);

    List<ProjectManager> findByProjectTypes(ProjectType projectType);
}
