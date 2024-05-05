package com.ironhack.locmgmt.repository;

import com.ironhack.locmgmt.model.users.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
