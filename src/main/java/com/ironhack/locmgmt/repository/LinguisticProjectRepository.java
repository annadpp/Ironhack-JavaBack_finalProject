package com.ironhack.locmgmt.repository;

import com.ironhack.locmgmt.model.projects.LinguisticProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinguisticProjectRepository extends JpaRepository<LinguisticProject, Long> {
}