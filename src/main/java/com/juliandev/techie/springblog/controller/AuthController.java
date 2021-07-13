package com.juliandev.techie.springblog.controller;

import com.juliandev.techie.springblog.dto.LoginRequest;
import com.juliandev.techie.springblog.dto.RegisterRequest;
import com.juliandev.techie.springblog.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody RegisterRequest registerRequest){
        return authService.signup(registerRequest);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest){
       return authService.login(loginRequest);
    }
}
