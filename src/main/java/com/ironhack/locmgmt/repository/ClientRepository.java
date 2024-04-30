package com.ironhack.locmgmt.repository;

import com.ironhack.locmgmt.model.Client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> getClientByName(String name);

    @Query("SELECT c.email FROM Client c WHERE c.name = :name")
    List<String> findEmailByName(String name);

    /*Add find client projects*/
}