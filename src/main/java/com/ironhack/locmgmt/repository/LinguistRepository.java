package com.ironhack.locmgmt.repository;

import com.ironhack.locmgmt.model.users.Linguist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinguistRepository extends JpaRepository<Linguist, Long> {
}
