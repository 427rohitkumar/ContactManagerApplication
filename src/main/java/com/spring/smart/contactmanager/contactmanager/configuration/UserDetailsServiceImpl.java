package com.spring.smart.contactmanager.contactmanager.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.spring.smart.contactmanager.contactmanager.dao.UserRepository;
import com.spring.smart.contactmanager.contactmanager.entities.User;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //how to fetch user from DataBase
        User user=userRepository.getUserByUserEmail(username);

        if (user==null) {
            throw new UsernameNotFoundException("Could not Found User !!");
        }

        CustomeUserDetails customeUserDetails=new CustomeUserDetails(user);
        return customeUserDetails;
    }
    
}
