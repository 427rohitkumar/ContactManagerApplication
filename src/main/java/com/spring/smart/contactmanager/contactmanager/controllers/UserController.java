package com.spring.smart.contactmanager.contactmanager.controllers;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.smart.contactmanager.contactmanager.dao.ContactRepository;
import com.spring.smart.contactmanager.contactmanager.dao.UserRepository;
import com.spring.smart.contactmanager.contactmanager.entities.Contact;
import com.spring.smart.contactmanager.contactmanager.entities.ContactDTO;
import com.spring.smart.contactmanager.contactmanager.entities.User;
import com.spring.smart.contactmanager.contactmanager.helper.Message;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private ContactRepository contactRep;

    @Autowired
    private UserRepository userRep;

    @ModelAttribute
    public void addCommonData(Model m, Principal principal) {
        String userName = principal.getName();
        User user = this.userRep.getUserByUserEmail(userName);
        m.addAttribute("user", user);
        System.out.println("User Name: " + userName);
        System.out.println("USER: " + user);
    }

    @RequestMapping("/index")
    public String dashboard(Model m, Principal principal) {
        m.addAttribute("title", "Dashboard");
        return "/normal/user_dashboard";
    }

    @GetMapping("/add-contact")
    public String openAddContactForm(Model m) {
        m.addAttribute("title", "Add-Contact");
        m.addAttribute("Contact", new Contact());

        return "/normal/add_contact_form";
    }

    @PostMapping("/process-contact")
    public String processContact(
            @ModelAttribute ContactDTO contactDTO,
            Principal principal,
            HttpSession session) {
        try {
            Contact contact = new Contact();
            contact.setName(contactDTO.getName());
            contact.setSecondName(contactDTO.getSecondName());
            contact.setWork(contactDTO.getWork());
            contact.setEmail(contactDTO.getEmail());
            contact.setPhoneString(contactDTO.getPhoneString());
            contact.setDescription(contactDTO.getDescription());
            MultipartFile file = contactDTO.getImage();
            System.out.println("File Info: " + file);
            if (!file.isEmpty()) {
                String fileName = file.getOriginalFilename();
                System.out.println("File Name: " + fileName);

                File saveFile = new ClassPathResource("static/images").getFile();

                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());

                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Images is Uploaded...");
                contact.setImage(fileName);
            } else {
                contact.setImage("default.png");
                System.out.println("File is Emply...");
            }

            // Associate contact with the logged-in user
            String userName = principal.getName();
            User user = this.userRep.getUserByUserEmail(userName);
            contact.setUser(user);

            user.getContacts().add(contact);

            this.userRep.save(user);
            System.out.println("=================================");
            // System.out.println("Data: " + contact);
            // showing message on page using session
            session.setAttribute("message", new Message("Your Contact is Addes !! Add More", "success"));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR:" + e.getMessage());
            session.setAttribute("message", new Message("Something when Wrong !! Try agian", "danger"));
        }

        return "/normal/add_contact_form";
    }

    @GetMapping("/show-contacts/{page}")
    public String showContacts(@PathVariable("page") Integer page, Model m, Principal principal) {
        m.addAttribute("title", "All-Contacts");
        String UserName = principal.getName();
        User user = this.userRep.getUserByUserEmail(UserName);
        Pageable pageable = PageRequest.of(page, 2);
        // user.getContacts();
        Page<Contact> contacts = this.contactRep.findContactsByUser(user.getId(), pageable);

        m.addAttribute("contacts", contacts);
        m.addAttribute("currentPage", page);
        m.addAttribute("totalPages", contacts.getTotalPages());
        return "normal/show_contacts";
    }

    @GetMapping("/{cId}/contact")
    public String showContactDetail(@PathVariable("cId") Integer cId, Model m, Principal principal) {
        System.out.println("===============================");
        System.out.println("Contact Id: " + cId);

        Optional<Contact> cotactOptional = this.contactRep.findById(cId);
        Contact contact = cotactOptional.get();

        String userName = principal.getName();
        User user = this.userRep.getUserByUserEmail(userName);

        if (user.getId() == contact.getUser().getId()) {
            m.addAttribute("contact", contact);
            m.addAttribute("title", contact.getName());
        }

        return "normal/contact_detail";
    }

    @PostMapping("/update-contact/{cId}")
    public String UpdateForm(@PathVariable("cId") Integer cId, Model m) {
        m.addAttribute("title", "Update-Contact");
        m.addAttribute("isPostMethod", true);

        Contact contact = this.contactRep.findById(cId).get();
        m.addAttribute("contact", contact);
        System.out.println("============ Contact Detail =====================");
        System.out.println(contact);
        return "normal/update_form";
    }

    @PostMapping("/delete/{cId}")
    public String deleteContact(@PathVariable("cId") Integer cId, Model m, HttpSession session) {
        Optional<Contact> contactOptional = this.contactRep.findById(cId);
        Contact contact = contactOptional.get();

        contact.setUser(null);
        this.contactRep.delete(contact);
        session.setAttribute("message", new Message("Contact Deleted Successfully", "success"));
        return "redirect:/user/show-contacts/0";
    }

    @GetMapping("/update-contact/{cId}")
    public String handleGetRequest(@PathVariable("cId") Integer cId, Model m) {
        m.addAttribute("title", "Update-Contact");
        m.addAttribute("isPostMethod", false);
        // Indicating that the method is not POST
        return "normal/update_form";
    }

    @PostMapping("/process-update")
    public String updateHandler(@ModelAttribute ContactDTO contactDTO, HttpSession session, Principal principal) {
        System.out.println("============== Process Update =============");
        System.out.println("CONTACT NAME:" + contactDTO.getName());

        try {
            // Fetch the existing contact using the ID from contactDTO
            Contact contact = this.contactRep.findById(contactDTO.getcId())
                    .orElseThrow(() -> new RuntimeException("Contact not found"));

            // Update the fields
            contact.setName(contactDTO.getName());
            contact.setSecondName(contactDTO.getSecondName());
            contact.setWork(contactDTO.getWork());
            contact.setEmail(contactDTO.getEmail());
            contact.setPhoneString(contactDTO.getPhoneString());
            contact.setDescription(contactDTO.getDescription());

            MultipartFile file = contactDTO.getImage();
            if (!file.isEmpty()) {

                
                String fileName = file.getOriginalFilename();
                File saveFile = new ClassPathResource("static/images").getFile();

                // Check if the old file exists and is not the default image
                if(!"defualt.png".equals(contact.getImage())){
                    File oldFile=new File(saveFile,contact.getImage());
                    if(oldFile.exists()){
                        oldFile.delete();
                        System.out.println("Old file is deleted!!");
                    }
                }
               


                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());

                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Images is Uploaded...");
                contact.setImage(fileName);
            } else {
                contact.setImage(contact.getImage());
                System.out.println("File is Emply...");
            }

            // Update the user (if needed)
            User user = this.userRep.getUserByUserEmail(principal.getName());
            contact.setUser(user);

            // Save the updated contact
            this.contactRep.save(contact);
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("message", new Message("Something went wrong. Please try again.", "danger"));
            return "redirect:/user/update-contact/" + contactDTO.getcId();
        }

        session.setAttribute("message", new Message("Contact updated successfully!", "success"));
        return "redirect:/user/"+contactDTO.getcId()+"/contact";
    }

    @GetMapping("/profile")
    public String userProfile(Model m,Principal principal){
        m.addAttribute("title", "Your-Profile");
        return "/normal/profile";
    }

}
