package com.smart.smartcontactmanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.smart.smartcontactmanager.dao.UserRepository;
import com.smart.smartcontactmanager.entities.User;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository ur;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        User user = ur.getUserByUserName(username);
        
        if (user==null) {
            throw new UsernameNotFoundException("Could not found user !!");
        }
        CustomUserDetails cud = new CustomUserDetails(user); 
        return cud;
    }
    
}
