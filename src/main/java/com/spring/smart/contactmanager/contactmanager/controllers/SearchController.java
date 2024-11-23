package com.spring.smart.contactmanager.contactmanager.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.spring.smart.contactmanager.contactmanager.dao.ContactRepository;
import com.spring.smart.contactmanager.contactmanager.dao.UserRepository;
import com.spring.smart.contactmanager.contactmanager.entities.Contact;
import com.spring.smart.contactmanager.contactmanager.entities.User;

@RestController
public class SearchController {
    @Autowired
    private UserRepository userRep;

    @Autowired
    private ContactRepository contactRep;
    
    @GetMapping("/search/{query}")
    public ResponseEntity<?> search(@PathVariable("query") String query, Principal principal){
        System.out.println("QUERY:"+query);
        User user=this.userRep.getUserByUserEmail(principal.getName());
        List<Contact> contact=this.contactRep.findByNameContainingAndUser(query, user);
        return ResponseEntity.ok(contact);
    }

}
