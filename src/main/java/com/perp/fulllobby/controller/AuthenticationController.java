package com.perp.fulllobby.controller;

import java.util.LinkedHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.perp.fulllobby.exception.EmailAlreadyTakenException;
import com.perp.fulllobby.exception.IncorrectCredentialsException;
import com.perp.fulllobby.model.LoginResponse;
import com.perp.fulllobby.model.MyUser;
import com.perp.fulllobby.services.MyUserService;
import com.perp.fulllobby.services.TokenService;

@Controller
@RequestMapping("/api/auth")
public class AuthenticationController {
    
    private final MyUserService userService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationController(MyUserService userService, TokenService tokenService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @ExceptionHandler({IncorrectCredentialsException.class})
    public ResponseEntity<String> handleIncorrectCredentials() {
        return new ResponseEntity<String>("The username or password entered was incorrect. Please try again.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({EmailAlreadyTakenException.class})
    public ResponseEntity<String> handleEmailTaken() {
        return new ResponseEntity<String>("The email is already in use. Please login with your password or follow prompts for forgotten passwords.", HttpStatus.CONFLICT);
    }


    // @TODO This is just a temporary method. Actual authentication logic will need to be implemented in SecurityConfig.java and AuthenticationController.java
    @PostMapping("/login")
    public LoginResponse authenticate(@RequestBody LinkedHashMap<String, String> body) {
        
        String username = body.get("username");
        String password = body.get("password");

        try {
            Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

            String token = tokenService.generateToken(auth);

            return new LoginResponse(userService.getByUsername(username), token);
        }
        catch (AuthenticationException e) {
            return new LoginResponse(null, "");
        }
    }

    @PostMapping("/verify")
    public MyUser verifyUser(@RequestBody LinkedHashMap<String, String> body) {
        Long code = Long.parseLong(body.get("code"));

        String username = body.get("username");

        return userService.verifyUser(username, code);
    }

}
