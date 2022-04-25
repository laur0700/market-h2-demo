package com.example.h2demo.service;

import com.example.h2demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {
    @Autowired
    UserService userService;

    public String login(String username, String password){
        List<User> registeredUsers = userService.getAll();

        for(User user : registeredUsers){
            if(username.equals(user.getUsername())){
                if(password.equals(user.getPassword())){
                    return "Logged!";
                }
            }
        }

        return null;
    }
}
