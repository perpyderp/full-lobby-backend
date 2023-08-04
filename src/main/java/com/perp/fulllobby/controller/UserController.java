package com.perp.fulllobby.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.perp.fulllobby.exception.UserNotFoundException;
import com.perp.fulllobby.model.MyUser;
import com.perp.fulllobby.repository.UserRepository;

@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {
    
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user")
    MyUser newUser(@RequestBody MyUser newUser) {
        return userRepository.save(newUser);
    }

    @GetMapping("/users")
    List<MyUser> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    MyUser user(@PathVariable Long id) {
        return userRepository.findById(id).orElseThrow(()->new UserNotFoundException(id));
    }

}
