package com.perp.fulllobby.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perp.fulllobby.model.MyUser;
import com.perp.fulllobby.model.Post;
import com.perp.fulllobby.services.MyUserService;
import com.perp.fulllobby.services.PostService;
import com.perp.fulllobby.services.TokenService;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin("*")
public class PostController {
    
    private PostService postService;
    private final MyUserService userService;
    private final TokenService tokenService;

    public PostController(PostService postService, TokenService tokenService, MyUserService userService) {
        this.postService = postService;
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @PostMapping
    public Post createPost(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody Post newPost) {

        MyUser user = userService.getByUsername(tokenService.getUsernameFromToken(token));

        return postService.createPost(newPost, user);
    }

    @GetMapping("/{userId}")
    public List<Post> getPostsByUserId(@PathVariable(name = "userId", required = true)Long id) {
        return postService.getPostsByUserId(id);
    }

    // @GetMapping("/{username}")
    // public List<Post> getPostsByUsername(@PathVariable(name = "username", required = true)String username) {
    //     return postService.getPostsByUsername(username);
    // }

}
