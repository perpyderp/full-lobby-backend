package com.perp.fulllobby.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
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
        List<Post> posts = new ArrayList<Post>();
        posts.add(new Post());
        return posts;
    }

    @PostMapping
    public Post createPost(@RequestBody Post newPost) {
        return postService.createPost(newPost);
    }

}
