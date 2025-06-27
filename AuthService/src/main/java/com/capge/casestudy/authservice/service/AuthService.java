package com.capge.casestudy.authservice.service;

import com.capge.casestudy.authservice.entity.User;
import com.capge.casestudy.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public Optional<User> saveUser(User credential) {
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        repository.save(credential);
        return repository.findById(credential.getId());
    }

    public String generateToken(String username , String role) {
        return jwtService.generateToken(username , role);
    }

    public String validateToken(String token) {
        jwtService.validateToken(token);
        String username = jwtService.extractUsername(token);
        return "Authenticated Credential Present With Username :  "+username;
    }


}