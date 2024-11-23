package com.spring.smart.contactmanager.contactmanager.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.smart.contactmanager.contactmanager.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u where u.email=:email")
    public User getUserByUserEmail(@Param("email") String email);
}
