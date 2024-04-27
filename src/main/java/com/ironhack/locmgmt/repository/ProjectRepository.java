package com.ironhack.locmgmt.repository;

import com.ironhack.locmgmt.model.projects.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
