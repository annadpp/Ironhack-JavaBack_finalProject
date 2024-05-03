package com.ironhack.locmgmt.repository;

import com.ironhack.locmgmt.model.enums.UserType;
import com.ironhack.locmgmt.model.users.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
/*    *//*To be totally implemented with security*//*
    Optional<User> findByUsername(String username);*/
}
