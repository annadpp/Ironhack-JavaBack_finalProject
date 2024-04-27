package com.ironhack.locmgmt.repository;

import com.ironhack.locmgmt.model.enums.UserType;
import com.ironhack.locmgmt.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByUserType(UserType userType);
}
