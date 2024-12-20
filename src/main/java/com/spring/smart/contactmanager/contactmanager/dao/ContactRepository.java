package com.spring.smart.contactmanager.contactmanager.dao;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.smart.contactmanager.contactmanager.entities.Contact;
import com.spring.smart.contactmanager.contactmanager.entities.User;

public interface ContactRepository extends JpaRepository<Contact,Integer>{
    
    @Query("from Contact as c where c.user.id=:userId")
    //current page-page
    //contact per page-5
    public Page<Contact> findContactsByUser(@Param("userId")int UserId,Pageable pePageable);

    public List<Contact> findByNameContainingAndUser(String name, User user);

}
