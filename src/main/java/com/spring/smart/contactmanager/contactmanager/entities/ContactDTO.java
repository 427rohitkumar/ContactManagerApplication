package com.spring.smart.contactmanager.contactmanager.entities;

import org.springframework.web.multipart.MultipartFile;

public class ContactDTO {
    private int cId;
    private String name; 
    private String secondName; 
    private String work; 
    private String email; 
    private String phoneString; 
    private MultipartFile image; // Use MultipartFile here 
    private String description;

    
    public int getcId() {
        return cId;
    }
    public void setcId(int cId) {
        this.cId = cId;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSecondName() {
        return secondName;
    }
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
    public String getWork() {
        return work;
    }
    public void setWork(String work) {
        this.work = work;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneString() {
        return phoneString;
    }
    public void setPhoneString(String phoneString) {
        this.phoneString = phoneString;
    }
    public MultipartFile getImage() {
        return image;
    }
    public void setImage(MultipartFile image) {
        this.image = image;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
   

    
}
