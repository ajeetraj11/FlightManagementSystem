package com.capge.casestudy.userservice.controller;



import com.capge.casestudy.userservice.dto.UpdateDto;
import com.capge.casestudy.userservice.entity.User;
import com.capge.casestudy.userservice.service.UserService;
import com.capge.casestudy.userservice.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/getById/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/getByUserMail/{userMail}")
    public ResponseEntity<?> getUserByMail(@PathVariable String userMail) {
        User user = userService.getUserByEmail(userMail);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "User not found", "email", userMail));
        }

        return ResponseEntity.ok(user);

    }

    @PutMapping("/updateProfile")
    public ResponseEntity<User> updateProfile(
            @RequestBody UpdateDto updateDto,
            @RequestHeader("Authorization") String authHeader
    ) {
        String userEmail = jwtUtils.extractEmail(authHeader);
        return userService.updateByEmail(userEmail, updateDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

}
