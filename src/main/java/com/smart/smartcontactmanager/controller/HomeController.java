package com.smart.smartcontactmanager.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.smart.smartcontactmanager.entities.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    // @Autowired  
    // private UserRepository ur;

    // @GetMapping("/test")
    // @ResponseBody // print direct return value
    // public String test() {

    //     User user = new User(0, "ss", "ss", "s", "s", false, "ss", "s");
    //     ur.save(user);
    //     return "Working";
    // }

    @GetMapping("/")
    public String home(Model m){
        m.addAttribute("title", "Home-Smart Contact Manager");
        return "home";
    }
    
    @GetMapping("/about")
    public String about(Model m){
        m.addAttribute("title", "About-Smart Contact Manager");
        return "about";
    }

    @GetMapping("/login")
    public String login(Model m){
        m.addAttribute("title", "Login-Smart Contact Manager");
        return "login";
    }
    
    @GetMapping("/signup")
    public String signup(Model m,HttpSession session){
        m.addAttribute("title", "Register-Smart Contact Manager");
        m.addAttribute("user", new User());
        session.setAttribute("msgp", false);
        return "signup";
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // Invalidate authentication
        SecurityContextHolder.clearContext();
        
        // Create a CookieClearingLogoutHandler to remove cookies
        new CookieClearingLogoutHandler("cookieName1", "cookieName2", "cookieName3").logout(request, response, null);
        
        // Invalidate session if needed (optional)
        request.getSession().invalidate();
        
        return "/login"; // Redirect to login page after logout
    }


}
