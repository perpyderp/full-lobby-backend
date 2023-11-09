package com.perp.fulllobby.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perp.fulllobby.model.Post;
import com.perp.fulllobby.services.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @PostMapping
    public Post createPost(@RequestBody Post newPost) {
        return postService.createPost(newPost);
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
