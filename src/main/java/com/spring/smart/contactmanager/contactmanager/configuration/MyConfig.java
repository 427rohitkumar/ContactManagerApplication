package com.spring.smart.contactmanager.contactmanager.configuration;

import org.springframework.security.config.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class MyConfig {

    @Bean
    public UserDetailsService getUserDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
        .authorizeHttpRequests(requests -> requests
         
            .requestMatchers("/admin/**").hasRole("ADMIN") // Only accessible by ADMIN
            .requestMatchers("/user/**").hasRole("USER")   // Only accessible by USER
            .requestMatchers("/**").permitAll()          // Publicly accessible page
            .anyRequest().authenticated()             // All other requests require authentication
            
        )
        .csrf(customizer->customizer.disable())
        .formLogin(form->form
          .loginPage("/signin")
          .loginProcessingUrl("/do-login")
          .defaultSuccessUrl("/user/index")
        //   .failureUrl("/signin")
          .permitAll())         // Default form-based login
        .httpBasic(Customizer.withDefaults())          // Enable basic authentication
        .logout(Customizer.withDefaults())             // Enable logout functionality
        .build();

        
    }

     @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setPasswordEncoder(this.passwordEncoder());
        provider.setUserDetailsService(this.getUserDetailsService());
        return  provider;
    }


    

}