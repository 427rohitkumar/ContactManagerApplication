package com.spring.smart.contactmanager.contactmanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.smart.contactmanager.contactmanager.dao.UserRepository;
import com.spring.smart.contactmanager.contactmanager.entities.User;
import com.spring.smart.contactmanager.contactmanager.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private  UserRepository userRepository;
    
    @GetMapping("/testing")
    @ResponseBody
    public String test(){
        User user=new User();
        user.setName("Rohit kumar");
        user.setEmail("rohitkumar@gmail.com");
        user.setAbout("this is about for rohit kuumar");
        userRepository.save(user);
        System.out.println("application is working.");
        return "hello";
    }


    @GetMapping("/")
    public String home(Model m){
        m.addAttribute("title", "Home - Page");
        return "home";
    }

    @GetMapping("/about")
    public String about(Model m){
        m.addAttribute("title", "Home - Page");
        return "about";
    }

    @GetMapping("/signin")
    public String signin(Model m){
        m.addAttribute("title", "Login - Page");
        return "login";
    }

    @GetMapping("/signup")
    public String singup(Model m,HttpSession session){
        m.addAttribute("title", "Register - Page");
        m.addAttribute("user", new User());
        session.removeAttribute("message"); 
        return "signup";
    }

    @PostMapping("/do-register")
    public String registerUser(@Valid @ModelAttribute("user") User user,BindingResult result1,@RequestParam(value="agreement",defaultValue = "false") boolean agreement, Model m,HttpSession session){

        if(result1.hasErrors()){
            System.out.println("ERROR: "+result1.toString());
            m.addAttribute("user",user);
            return "signup";
        }

       try {
            if(!agreement){
                System.out.println("Please kindly Check the term and condtion");
                throw new Exception("You Have not agreed the term and conditions");
            }
            System.out.println("after Agreement!!");

            

            user.setRole("ROLE_ADMIN");
            user.setEnabled(true);
            user.setIamgeUrl("default.png");
            user.setPassword(encoder.encode(user.getPassword()));
            System.out.println("Agreement: "+agreement);
            System.out.println("user: "+user);
            // m.addAttribute("title", "Register - Page");
            this.userRepository.save(user);
            m.addAttribute("user", new User());

            session.setAttribute("message", new Message("Successfully Registerd !!","alert-success"));
            return "signup";
       } catch (Exception e) {
            m.addAttribute("user", user);
            session.setAttribute("message", new Message("something went wrong!!"+e.getMessage(),"alert-danger"));
            return "signup";
       }


        
    }
}
