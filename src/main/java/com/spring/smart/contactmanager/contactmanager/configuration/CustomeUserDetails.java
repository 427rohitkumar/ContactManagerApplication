package com.spring.smart.contactmanager.contactmanager.configuration;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.spring.smart.contactmanager.contactmanager.entities.User;

public class CustomeUserDetails implements UserDetails {

    private User user;

    
   
    public CustomeUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority simpleGranted=new SimpleGrantedAuthority(user.getRole());
        return List.of(simpleGranted);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {        
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {       
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {      
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {       
        return true;
    }

    
}
