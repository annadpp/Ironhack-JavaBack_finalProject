package com.ironhack.locmgmt.repository;

import com.ironhack.locmgmt.model.enums.LinguisticTechnology;
import com.ironhack.locmgmt.model.projects.LinguisticProject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LinguisticProjectRepository extends JpaRepository<LinguisticProject, Long> {
    List<LinguisticProject> findByLinguisticTechnology(LinguisticTechnology linguisticTechnology);

    Optional<LinguisticProject> findByName(String name);
}