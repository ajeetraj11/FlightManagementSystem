package com.capge.casestudy.authservice.service;

import com.capge.casestudy.authservice.entity.User;
import com.capge.casestudy.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public Optional<User> getUserByUsername(String userName){

        return userRepository.findByUsername(userName);
    }

    public Optional<User> getUserByMail(String userMail) {
        return userRepository.findByEmail(userMail);
    }
}
