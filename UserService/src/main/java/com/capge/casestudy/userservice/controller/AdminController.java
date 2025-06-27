package com.capge.casestudy.userservice.controller;


import com.capge.casestudy.userservice.dto.UpdateDto;
import com.capge.casestudy.userservice.entity.User;
import com.capge.casestudy.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/admin/detail")
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/get")
    public String getMessage(){
        return "This is the testing message";
    }
    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
	   userService.deleteUser(id);
	   return ResponseEntity.noContent().build();
    }

    @PutMapping("/UpdateById/{id}")
    public ResponseEntity<User> UpdatedUser(@PathVariable Long id , @Valid @RequestBody UpdateDto user) {
        return ResponseEntity.ok( userService.updateUserById(id , user));
    }

@GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
	   return ResponseEntity.ok(userService.getAllUsers());
    }
}
