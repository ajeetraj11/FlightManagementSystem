package com.capge.casestudy.authservice.service;

import com.capge.casestudy.authservice.entity.User;
import com.capge.casestudy.authservice.exception.UserNotExistsExceptions;
import com.capge.casestudy.authservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Slf4j
@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email)throws UsernameNotFoundException{

        log.info("Extracting the User from the Database to Creating Authentication Object for Security Context Holder");
        Optional<User> credential =repository.findByEmail(email);

        return credential.map(CustomUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("Credential not found with name :" + email));
    }
}