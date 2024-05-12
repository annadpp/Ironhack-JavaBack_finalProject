package com.ironhack.locmgmt.repository;

import com.ironhack.locmgmt.model.Task;
import com.ironhack.locmgmt.model.users.Linguist;
import com.ironhack.locmgmt.model.enums.BillingStatus;
import com.ironhack.locmgmt.model.enums.ProjectType;

import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Date;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByDeadlineBetween(Date start, Date end);

    List<Task> findByTimeRemainingLessThan(Duration duration);

    List<Task> findByProjectType(ProjectType taskType);

    List<Task> findByBillingStatus(BillingStatus billingStatus);

    List<Task> findByLinguist(Linguist linguist);

    boolean existsByName(String name);

    List<Task> findByPagesGreaterThan(Integer pages);
    List<Task> findByPagesLessThan(Integer pages);

    List<Task> findByNewWordsLessThanAndFuzzyWordsLessThan(Integer newWords, Integer fuzzyWords);

    List<Task> findByNewWordsGreaterThanAndFuzzyWordsGreaterThan(Integer newWords, Integer fuzzyWords);

  List<Task> findByTotalWordsGreaterThan(Integer totalWords);

  List<Task> findByTotalWordsLessThan(Integer totalWords);

    List<Task> findByProjectId(Long projectId);


}