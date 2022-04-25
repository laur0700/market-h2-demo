package com.example.h2demo.controller;

import com.example.h2demo.dto.UserDTO;
import com.example.h2demo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("login")
    public void login(@RequestBody UserDTO credentials){
        System.out.println(authService.login(credentials.getUsername(), credentials.getPassword()));
    }
}
