package com.perp.fulllobby.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perp.fulllobby.model.MyUser;
import com.perp.fulllobby.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    
    @Autowired
    private final UserService userService;

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    
    @PostMapping("/register")
    public MyUser registerUser(@RequestBody MyUser user) {
        return userService.registerUser(user);
    }

}
