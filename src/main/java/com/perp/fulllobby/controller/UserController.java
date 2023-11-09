package com.perp.fulllobby.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.http.HttpResponse;
import com.perp.fulllobby.model.MyUser;
import com.perp.fulllobby.model.RegistrationObject;
import com.perp.fulllobby.services.MyUserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private final MyUserService userService;

    public UserController(MyUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public MyUser getUser(@PathVariable(value="id", required = true)Long id) {
        return userService.getUserById(id);
    }

    @GetMapping
    public List<MyUser> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public MyUser addUser(@RequestBody RegistrationObject newUser) {
        return userService.registerUser(newUser);
    }

    @GetMapping("/{id}/friends")
    public List<MyUser> getUserFriends(@PathVariable(value="id", required = true)Long id) {
        return userService.getUserFriends(id);
    }

    @DeleteMapping("/{id}/friends")
    public ResponseEntity<HttpResponse> removeFriend(@RequestBody MyUser removeFriend) {
        return userService.removeFriend(removeFriend);
    }

}
