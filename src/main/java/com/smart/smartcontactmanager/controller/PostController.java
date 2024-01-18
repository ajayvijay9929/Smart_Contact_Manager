package com.smart.smartcontactmanager.controller;

import java.io.File;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.smartcontactmanager.dao.UserRepository;
import com.smart.smartcontactmanager.entities.User;
import com.smart.smartcontactmanager.helper.GEmailSender;
import com.smart.smartcontactmanager.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class PostController {
    private int otp;
    @Autowired
    private UserRepository ur;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/otp")
    @ResponseBody
    public boolean loginOTP(@RequestParam String email) {
        System.out.println("i am in otp");
        System.out.println(email);
        GEmailSender gEmailSender=new GEmailSender();
        String to="2021pcecrvijay020@poornima.org"; // this is for testing purpose
        // String to=email;
        String from="vijayyadavworks@gmail.com";
        String subject="Contact Manager OTP";
        Random random = new Random();
        otp =  10000 + random.nextInt(90000);
        String text= "Your Signup Otp   " + otp + "    don't share OTP with anyone";
        boolean b = gEmailSender.sendEmail(to, from, subject, text);
        if (b) System.out.println("mail send ");
        else System.out.println("something miss");
        return b;
    }


    // this is use for send the fileattachment using email
    @GetMapping("/sendfile")
    public boolean emailAttachFile(){
        System.out.println("i am here");
        GEmailSender gEmailSender=new GEmailSender();
        String to="2021pcecrvijay020@poornima.org"; // this is for testing purpose
        // String to=email;
        String from="vijayyadavworks@gmail.com";
        String subject="Contact Manager OTP";
        Random random = new Random();
        otp =  10000 + random.nextInt(90000);
        String text= "Your Signup Otp   " + otp + "    don't share OTP with anyone";
        File file=new File("E:\\OneDrive\\Vijay\\download.jpg");
        boolean b = gEmailSender.sendEmailAttachment(to, from, subject, text,file);
        if (b) System.out.println("mail send ");
        else System.out.println("something miss");
        return b;
        
    }

    @PostMapping("do_signup")
    public String signup(@Valid @ModelAttribute("user") User user, BindingResult br ,Model m,@RequestParam("otp") int uotp,HttpSession session){
        
        System.out.println(otp);
        if(uotp != otp){
            m.addAttribute("user", user);
            session.setAttribute("message", new Message("Incorrect OTP !!", "alert alert-danger"));
            return "signup";
        }
        user.setEnabled(true);
        user.setRole("ROLE_USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
      
        if (br.hasErrors()) {
            System.out.println("this is error " + br);
            return "signup";
        }
        try {
            System.out.println("users " + user);
            ur.save(user);
            m.addAttribute("user", new User());
            session.setAttribute("message", new Message("Successfully Registered !!", "alert alert-success"));
            return "signup";
            
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute("user", user);
            session.setAttribute("message", new Message("Something Went wrong !!", "alert alert-danger"));
        }
        otp=0;
         return "signup";
    }    
}
