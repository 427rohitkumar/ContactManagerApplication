package com.spring.smart.contactmanager.contactmanager.entities;




import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Entity
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @NotBlank(message="Name field is required!!")
    @Size(min=2, max=20,message="Minimus 2 or maximum 20 character allowed!!")
    private String name;

    @Column(unique = true)
    @NotBlank(message="Emai must be required!!")
    private String email;
    private String password;
    private String role;
    private boolean enabled;
    private String iamgeUrl;

    @Column(length = 50)
    private String about;


    @OneToMany(cascade = CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="user")
    private List<Contact> contacts=new ArrayList<>();

    public User() {

    }

    public User(int id, String name, String email, String password, String role, boolean enabled, String iamgeUrl,
            String about) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
        this.iamgeUrl = iamgeUrl;
        this.about = about;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getIamgeUrl() {
        return iamgeUrl;
    }

    public void setIamgeUrl(String iamgeUrl) {
        this.iamgeUrl = iamgeUrl;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    // @Override
    // public String toString() {
    //     return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", role=" + role
    //             + ", enabled=" + enabled + ", iamgeUrl=" + iamgeUrl + ", about=" + about + ", contacts=" + contacts
    //             + "]";
    // }



}
