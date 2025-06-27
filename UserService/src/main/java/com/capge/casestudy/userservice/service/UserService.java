package com.capge.casestudy.userservice.service;



import com.capge.casestudy.userservice.dto.UpdateDto;
import com.capge.casestudy.userservice.entity.User;
import com.capge.casestudy.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        log.info("Returned All User Process Initiated");
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> updateByEmail(String userMail, UpdateDto newUserDetails) {
        User user = userRepository.findByEmail(userMail);
        log.info("User Name in the Database ,{} and the userName in the token is ,{} " ,user.getEmail() , userMail);
            user.setUsername(newUserDetails.getUsername());
            user.setPhone(newUserDetails.getPhone());
            user.setAge(newUserDetails.getAge());
            user.setGender(newUserDetails.getGender());
            return Optional.of(userRepository.save(user));

    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
        log.info("User Successfully Deleted With User Id {}", id);
    }

    public User getUserByEmail(String email) {
        log.info("Get User By UserName Process Initiated");
        return userRepository.findByEmail(email);
    }

    public User updateUserById(Long id , UpdateDto user){
        User exixitingUser =  userRepository.findById(id).get();
        exixitingUser.setAge(user.getAge());
        exixitingUser.setPhone(user.getPhone());
        exixitingUser.setUsername(user.getUsername());
        exixitingUser.setEmail(user.getEmail());
        exixitingUser.setRole(user.getRole());
        exixitingUser.setGender(user.getGender());

        return userRepository.save(exixitingUser);

    }


}
