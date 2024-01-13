package com.smart.smartcontactmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.smart.smartcontactmanager.dao.UserRepository;
import com.smart.smartcontactmanager.entities.User;
import com.smart.smartcontactmanager.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class PostController {
    
    @Autowired
    private UserRepository ur;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("do_signup")
    public String signup(@Valid @ModelAttribute("user") User user, BindingResult br ,Model m,HttpSession session){
        user.setEnabled(true);
        user.setRole("ROLE_USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
      
        if (br.hasErrors()) {
            System.out.println("this is error " + br);
            m.addAttribute("user", user);
            return "signup";
        }
        try {
            System.out.println("users " + user);
            ur.save(user);
            m.addAttribute("user", new User());
            session.setAttribute("message", new Message("Successfully Registered !!", "alert alert-success"));
            session.setAttribute("msgp", true);
            return "signup";
            
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute("user", user);
            session.setAttribute("msgp", true);
            session.setAttribute("message", new Message("Something Went wrong !!", "alert alert-danger"));
        }
         return "signup";
    }    
}
