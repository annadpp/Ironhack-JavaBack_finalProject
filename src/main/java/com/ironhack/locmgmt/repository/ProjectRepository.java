package com.ironhack.locmgmt.repository;

import com.ironhack.locmgmt.model.projects.Project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByTotalBudgetGreaterThan(BigDecimal totalBudget);

    List<Project> findByTotalBudgetLessThan(BigDecimal totalBudget);
}
