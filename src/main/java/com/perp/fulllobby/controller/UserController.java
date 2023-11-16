package com.perp.fulllobby.controller;

import java.util.List;
import java.util.Set;
import java.util.LinkedHashMap;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.client.http.HttpResponse;
import com.perp.fulllobby.exception.CannotFindFriendRequestException;
import com.perp.fulllobby.exception.CannotFriendSelf;
import com.perp.fulllobby.exception.UnableToSaveAvatarException;
import com.perp.fulllobby.exception.UnableToSaveBannerException;
import com.perp.fulllobby.exception.UnableToSendFriendRequest;
import com.perp.fulllobby.model.MyUser;
import com.perp.fulllobby.services.MyUserService;
import com.perp.fulllobby.services.TokenService;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UserController {
    
    private final MyUserService userService;
    private final TokenService tokenService;

    public UserController(MyUserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @ExceptionHandler({UnableToSaveAvatarException.class})
    public ResponseEntity<String> handleAvatarException() {
        return new ResponseEntity<String>("Unable to process the image", HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler({UnableToSendFriendRequest.class})
    public ResponseEntity<String> handleFriendRequestException() {
        return new ResponseEntity<String>("Unable to send friend request", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({CannotFriendSelf.class})
    public ResponseEntity<String> handleFriendSelfException() {
        return new ResponseEntity<String>("You friend request yourself. ", HttpStatus.FORBIDDEN);
    }

    @GetMapping("/verify")
    public MyUser verifyIdentity(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

        String username = tokenService.getUsernameFromToken(token);

        return userService.getByUsername(username);

    }

    @PostMapping("/upload/avatar")
    public MyUser uploadAvatar(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestParam("image") MultipartFile file) throws UnableToSaveAvatarException{

        String username = tokenService.getUsernameFromToken(token);

        return userService.setAvatar(username, file);
    }

    @PostMapping("/upload/banner")
    public MyUser uploadBanner(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestParam("image") MultipartFile file) throws UnableToSaveBannerException{

        String username = tokenService.getUsernameFromToken(token);

        return userService.setBanner(username, file);
    }

    @PostMapping("/add-friend")
    public ResponseEntity<String> addFriend(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody LinkedHashMap<String, String> body) 
        throws UnableToSendFriendRequest, CannotFriendSelf {

        String loggedInUser = tokenService.getUsernameFromToken(token);
        String addedUser = body.get("addedUser");

        return userService.sendFriendRequest(loggedInUser, addedUser);
    }

    @PutMapping("/accept")
    public Set<MyUser> acceptFriendRequest(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody LinkedHashMap<String, String> body) throws CannotFindFriendRequestException{

        String loggedInUser = tokenService.getUsernameFromToken(token);
        String acceptedUser = body.get("acceptedUser");

        return userService.acceptFriend(loggedInUser, acceptedUser);
    }
    

    @PutMapping("/")
    public MyUser updateUser(@RequestBody MyUser user) {
        return userService.updateUser(user);
    }

    @GetMapping("/{username}")
    public MyUser getUser(@PathVariable(name = "username") String username) {
        return userService.getByUsername(username);
    }

    @GetMapping
    public List<MyUser> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{username}/friends")
    public Set<MyUser> getUserFriends(@PathVariable("username") String username) {
        return userService.getUserFriends(username);
    }

    @DeleteMapping("/{id}/friends")
    public ResponseEntity<HttpResponse> removeFriend(@RequestBody MyUser removeFriend) {
        return userService.removeFriend(removeFriend);
    }

}
