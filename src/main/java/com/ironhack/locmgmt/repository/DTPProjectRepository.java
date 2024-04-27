package com.ironhack.locmgmt.repository;

import com.ironhack.locmgmt.model.projects.DTPProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DTPProjectRepository extends JpaRepository<DTPProject, Long> {
}
