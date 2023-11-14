package com.perp.fulllobby.controller;

import java.util.LinkedHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.perp.fulllobby.exception.IncorrectCredentialsException;
import com.perp.fulllobby.model.MyUser;
import com.perp.fulllobby.services.MyUserService;

@Controller
@RequestMapping("/api/auth")
public class AuthenticationController {
    
    private final MyUserService userService;

    public AuthenticationController(MyUserService userService) {
        this.userService = userService;
    }

    @ExceptionHandler({IncorrectCredentialsException.class})
    public ResponseEntity<String> handleIncorrectCredentials() {
        return new ResponseEntity<String>("The username or password entered was incorrect. Please try again.", HttpStatus.BAD_REQUEST);
    }


    // @TODO This is just a temporary method. Actual authentication logic will need to be implemented in SecurityConfig.java and AuthenticationController.java
    @PostMapping("/")
    public MyUser authenticate(@RequestBody LinkedHashMap<String, String> body) {
        
        String username = body.get("username");
        String password = body.get("password");

        System.out.println(password + " " + username);

        return userService.login(username, password);
    }

}
