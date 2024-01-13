package com.smart.smartcontactmanager.controller;

import java.util.*;
import java.io.IOException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.smartcontactmanager.dao.ContactRepository;
import com.smart.smartcontactmanager.dao.UserRepository;
import com.smart.smartcontactmanager.entities.Contact;
import com.smart.smartcontactmanager.entities.User;
import com.smart.smartcontactmanager.helper.Message;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository ur;
    @Autowired
    private ContactRepository cr;
    User user;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // use for add common data for all requests
    @ModelAttribute
    private void addCommonData(Model model, Principal principal) {
        String username = principal.getName();
        user = ur.getUserByUserName(username);
        model.addAttribute("user", user);
    }

    // deshboard home
    @GetMapping("/index")
    public String normalDeshboard(Model model, Principal principal) {
        return "normal/user_deshboard";
    }

    // add contact
    @GetMapping("/add-contact")
    public String openAddContactForm(Model m) {
        m.addAttribute("title", "Add Contact");
        m.addAttribute("contact", new Contact());
        return "normal/add_contact_form";
    }

    // view contact
    @GetMapping("/view-contact/{page}")
    public String viewContact(@PathVariable("page") Integer page, Model m) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Contact> contacts = cr.findContactsByUserP(user.getId(), pageable);
        m.addAttribute("title", "View Contact");
        m.addAttribute("currentPage", page);
        m.addAttribute("totalPages", contacts.getTotalPages());
        m.addAttribute("contacts", contacts);
        return "normal/view_contact";
    }

    // this for show the single contact info
    @GetMapping("/{cId}/contact")
    public String showContactInfo(@PathVariable("cId") int cId, Model m) {
        Optional<Contact> contactOptional = this.cr.findById(cId);
        Contact contact = contactOptional.get();

        if (user.getId() == contact.getUser().getId()) {
            m.addAttribute("contact", contact);
            m.addAttribute("imgurl", Base64.getEncoder().encodeToString(contact.getImage()));
            m.addAttribute("title", contact.getName());
        }
        return "normal/contact_info";
    }

    // update contact
    @GetMapping("/{cId}/update")
    public String updateContact(@PathVariable("cId") int id, Model m) {
        Contact contact = cr.findById(id).get();
        m.addAttribute("contact", contact);
        m.addAttribute("imgurl", Base64.getEncoder().encodeToString(contact.getImage()));
        m.addAttribute("title", "Update contact");
        return "normal/update_contact";
    }

    // delete contact
    @GetMapping("/{cId}/delete/{currentPage}")
    public String deleteContact(@PathVariable("cId") int id, @PathVariable("currentPage") int currentPage,
            HttpSession session) {
        Contact contact = cr.findById(id).get();
        if (user.getId() == contact.getUser().getId()) {
            contact.setUser(null);
            cr.delete(contact);
            session.setAttribute("message", new Message("Contact deleted succesfully...", "success"));
        }
        return "redirect:/user/view-contact/" + currentPage;
    }

    @GetMapping("/profile")
    public String profilePage() {
        return "normal/profile";
    }

    @GetMapping("/setting")
    public String settingPage() {
        return "normal/setting";
    }

    // for logout
    @GetMapping("/logoutPage")
    public String conformLogout(){
        return "normal/logout";
    }

    // post request part start form here

    // add contact
    @PostMapping("/process-contact")
    public String processContact(@RequestParam("name") String name, @RequestParam("secondName") String secondName,
            @RequestParam("work") String work, @RequestParam("email") String email, @RequestParam("phone") String phone,
            @RequestParam("description") String description, @RequestParam("image") MultipartFile imageFile,
            HttpSession session) throws IOException {

        byte[] imageBytes = imageFile.getBytes();
        Contact contact = new Contact(name, secondName, work, email, phone, imageBytes, description, user);
        user.getContacts().add(contact);
        this.ur.save(user);
        session.setAttribute("message", new Message("Contact saved", ""));
        return "normal/add_contact_form";
    }

    // update contact
    @PostMapping("/update-contact")
    public String updateContact(@RequestParam("cId") int cId, @RequestParam("name") String name,
            @RequestParam("secondName") String secondName,
            @RequestParam("work") String work, @RequestParam("email") String email, @RequestParam("phone") String phone,
            @RequestParam("description") String description, @RequestParam("image") MultipartFile imageFile,
            HttpSession session) throws IOException {

        Contact contact = new Contact();
        if (!imageFile.isEmpty()) {
            byte[] imageBytes = imageFile.getBytes();
            contact.setImage(imageBytes);
        } else {
            Contact c = cr.findById(cId).get();
            byte[] imageBytes = c.getImage();
            contact.setImage(imageBytes);
        }
        contact.setAllContact(cId, name, secondName, work, email, phone, description, user);
        cr.save(contact);
        session.setAttribute("message", new Message("Contact updated succesfully...", "success"));
        return "redirect:/user/" + cId + "/contact";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam("password") String password, HttpSession session) {
        session.setAttribute("message", new Message("Password Changed Successfully", "success"));
        System.out.println(password);
        user.setPassword(passwordEncoder.encode(password));
        ur.save(user);
        return "normal/setting";
    }

}
