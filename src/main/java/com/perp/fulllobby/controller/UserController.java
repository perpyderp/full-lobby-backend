package com.perp.fulllobby.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.client.http.HttpResponse;
import com.perp.fulllobby.exception.UnableToSaveAvatarException;
import com.perp.fulllobby.model.MyUser;
import com.perp.fulllobby.services.ImageService;
import com.perp.fulllobby.services.MyUserService;
import com.perp.fulllobby.services.TokenService;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UserController {
    
    private final MyUserService userService;
    private final TokenService tokenService;
    private final ImageService imageService;

    public UserController(MyUserService userService, TokenService tokenService, ImageService imageService) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.imageService = imageService;
    }

    @ExceptionHandler({UnableToSaveAvatarException.class})
    public ResponseEntity<String> handleAvatarException() {
        return new ResponseEntity<String>("Unable to process the image", HttpStatus.NOT_ACCEPTABLE);
    }

    @GetMapping("/verify")
    public MyUser verifyIdentity(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String username = "";
        MyUser user;

        if(token.substring(0, 6).equals("Bearer")) {
            String strippedToken = token.substring(7);
            username = tokenService.getUsernameFromToken(strippedToken);
        }
        try {
            user = userService.getByUsername(username);

        }
        catch(Exception e) {
            user = null;
        }

        return user;
    }

    @PostMapping("/upload/avatar")
    public ResponseEntity<String> uploadAvatar(@RequestParam("image") MultipartFile file) throws UnableToSaveAvatarException{
        String uploadAvatar = imageService.uploadAvatar(file, "avatar");

        return ResponseEntity.status(HttpStatus.OK).body(uploadAvatar);
    }

    @GetMapping("/{id}")
    public MyUser getUser(@PathVariable(value="id", required = true)Long id) {
        return userService.getUserById(id);
    }

    @GetMapping
    public List<MyUser> getAllUsers() {
        return userService.getAllUsers();
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
