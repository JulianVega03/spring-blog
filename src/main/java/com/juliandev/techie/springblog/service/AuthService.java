package com.juliandev.techie.springblog.service;

import com.juliandev.techie.springblog.dto.LoginRequest;
import com.juliandev.techie.springblog.dto.RegisterRequest;
import com.juliandev.techie.springblog.exception.PostNotFoundException;
import com.juliandev.techie.springblog.model.User;
import com.juliandev.techie.springblog.repository.UserRepository;
import com.juliandev.techie.springblog.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    public ResponseEntity signup(RegisterRequest registerRequest){
            User user = new User();
            user.setUserName(registerRequest.getUsername());
            user.setEmail(registerRequest.getEmail());
            user.setPassword(encodePassword(registerRequest.getPassword()));
            try{
                userRepository.save(user);
                return  ResponseEntity.ok().build();
            }catch (DataAccessException e){
                System.out.println("mensaje de la excepcion: " +e.getMessage());
                return  ResponseEntity.badRequest().build();
            }
    }

    public String login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String authenticationToken = jwtProvider.generateToken(authenticate);
        return authenticationToken;
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public Optional<org.springframework.security.core.userdetails.User> getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Optional.of(principal);
    }
}
