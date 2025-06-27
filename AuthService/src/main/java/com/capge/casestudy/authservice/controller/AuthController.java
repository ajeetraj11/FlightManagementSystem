package com.capge.casestudy.authservice.controller;


import com.capge.casestudy.authservice.dto.AuthRequest;
import com.capge.casestudy.authservice.dto.JwtResponse;
import com.capge.casestudy.authservice.entity.User;
import com.capge.casestudy.authservice.exception.CredentialMistMatchException;
import com.capge.casestudy.authservice.exception.UserNotExistsExceptions;
import com.capge.casestudy.authservice.repository.UserRepository;
import com.capge.casestudy.authservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService service;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<Optional<User>> addNewUser(@Valid @RequestBody User user) {
        return ResponseEntity.ok(service.saveUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> getToken(  @Valid @RequestBody AuthRequest authRequest) throws UserNotExistsExceptions {


        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserEmail(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {

            Optional<User> user = userRepository.findByEmail(authRequest.getUserEmail());
            if(user.isEmpty()) throw new UserNotExistsExceptions("Credential Not Exists in the System ");
            log.info("ROLE IS "+ user.get().getRole());
            return ResponseEntity.ok( new JwtResponse(service.generateToken(user.get().getEmail() , user.get().getRole()) , user.get().getRole()));
        } else {
            throw new CredentialMistMatchException("Provided Credential are not authenticated to access the resource");
        }
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {

       return service.validateToken(token);

    }
}
