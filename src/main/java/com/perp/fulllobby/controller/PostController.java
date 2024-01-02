package com.perp.fulllobby.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.perp.fulllobby.dto.PostDTO;
import com.perp.fulllobby.model.Like;
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
    public List<PostDTO> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/recent")
    public List<Post> getRecentPosts() {
        return postService.getRecentPosts();
    }

    @PostMapping
    public Post createPost(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody Post newPost) {

        MyUser user = userService.getByUsername(tokenService.getUsernameFromToken(token));

        return postService.createPost(newPost, user);
    }

    @GetMapping("/{userId}")
    public List<Post> getPostsByUserId(@PathVariable(name = "userId", required = true)UUID id) {
        return postService.getPostsByUserId(id);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<Post>> getPaginatedPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Post> posts = postService.getPaginatedPosts(0, 2);
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/like")
    public Like likePost(@RequestBody LinkedHashMap<String, UUID> body) {
        UUID postId = body.get("postId");
        UUID userId = body.get("userId");

        return postService.likePost(postId, userId);
    }
    

    // @GetMapping("/{username}")
    // public List<Post> getPostsByUsername(@PathVariable(name = "username", required = true)String username) {
    //     return postService.getPostsByUsername(username);
    // }

}
