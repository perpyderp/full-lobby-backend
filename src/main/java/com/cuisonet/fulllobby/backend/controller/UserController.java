package com.cuisonet.fulllobby.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cuisonet.fulllobby.backend.model.User;
import com.cuisonet.fulllobby.backend.repository.UserRepository;

// The @RestController annotation allows to handle all REST APIs such as GET, POST, Delete, PUT requests
@RestController
public class UserController {
    
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user")
    User newUser(@RequestBody User newUser){
        return userRepository.save(newUser);
    }

    @GetMapping("/users")
    List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
