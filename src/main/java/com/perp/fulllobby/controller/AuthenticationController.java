package com.perp.fulllobby.controller;

import java.util.LinkedHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perp.fulllobby.exception.EmailAlreadyTakenException;
import com.perp.fulllobby.exception.EmailFailedToSendException;
import com.perp.fulllobby.exception.IncorrectVerificationCodeException;
import com.perp.fulllobby.exception.UserDoesNotExistException;
import com.perp.fulllobby.model.MyUser;
import com.perp.fulllobby.model.RegistrationObject;
import com.perp.fulllobby.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    
    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @ExceptionHandler({EmailAlreadyTakenException.class})
    public ResponseEntity<String> handleEmailTaken() {
        return new ResponseEntity<String>("Email provided is already in use", HttpStatus.CONFLICT);
    }
    
    @PostMapping("/register")
    public MyUser registerUser(@RequestBody RegistrationObject regObject) {
        return userService.registerUser(regObject);
    }

    @ExceptionHandler({UserDoesNotExistException.class})
    public ResponseEntity<String> userDoesNotExist() {
        return new ResponseEntity<String>("The user was not found", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/phone")
    public MyUser updatePhoneNumber(@RequestBody LinkedHashMap<String, String> body) {
        String username = body.get("username");
        String phone = body.get("phone");

        MyUser user = userService.getUserByUsername(username);

        user.setPhone(phone);

        return userService.updateUser(user);
    }

    @ExceptionHandler({EmailFailedToSendException.class})
    public ResponseEntity<String> handleEmailFailed() {
        return new ResponseEntity<String>("Email failed to send, please try again momentarily", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/email/code")
    public ResponseEntity<String> createVerificationCode(@RequestBody LinkedHashMap<String, String> body) {
        userService.generateVerification(body.get("username"));

        return new ResponseEntity<String>("Verification code generated, email sent", HttpStatus.OK);
    }

    @ExceptionHandler({IncorrectVerificationCodeException.class})
    public ResponseEntity<String> handleIncorrectVerification() {
        return new ResponseEntity<String>("The code provided does not match user's verification code.", HttpStatus.CONFLICT);
    }

    @PostMapping("/email/verify")
    public MyUser verifyEmail(@RequestBody LinkedHashMap<String, String> body) {
        Long code = Long.parseLong(body.get("code"));

        String username = body.get("username");

        return userService.verifyEmail(username, code);
    }

    @PutMapping("/update/password")
    public MyUser updatePassword(@RequestBody LinkedHashMap<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        return userService.setPassword(username, password);
    }

}
