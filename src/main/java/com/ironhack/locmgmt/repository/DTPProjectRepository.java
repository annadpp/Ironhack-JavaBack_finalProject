package com.ironhack.locmgmt.repository;

import com.ironhack.locmgmt.model.enums.DTPTechnology;
import com.ironhack.locmgmt.model.projects.DTPProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DTPProjectRepository extends JpaRepository<DTPProject, Long> {
    List<DTPProject> findByDtpTechnology(DTPTechnology dtpTechnology);
    List<DTPProject> findByPagesGreaterThan(Integer pages);
    List<DTPProject> findByPagesLessThan(Integer pages);
}
