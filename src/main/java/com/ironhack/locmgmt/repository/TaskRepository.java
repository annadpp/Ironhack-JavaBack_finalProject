package com.ironhack.locmgmt.repository;

import com.ironhack.locmgmt.model.Task;
import com.ironhack.locmgmt.model.enums.BillingStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Duration;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /*add updateTotalPrice*/

    @Query("UPDATE Task t SET t.billingStatus = :billingStatus WHERE t.id = :taskId")
    void updateBillingStatus(@Param("taskId") Long taskId, @Param("billingStatus") BillingStatus billingStatus);
}