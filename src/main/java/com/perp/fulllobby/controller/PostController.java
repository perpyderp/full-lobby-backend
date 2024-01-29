package com.perp.fulllobby.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.perp.fulllobby.dto.PostDTO;
import com.perp.fulllobby.exception.UserNotFoundException;
import com.perp.fulllobby.model.MyUser;
import com.perp.fulllobby.model.Post;
import com.perp.fulllobby.model.ToggleLikeResponse;
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

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<String> handlerUserNotFoundException() {
        return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({InvalidBearerTokenException.class})
    public ResponseEntity<String> handlerInvalidBearerTokenException() {
        return new ResponseEntity<String>("Invalid Bearer token", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping
    public List<PostDTO> getAllPosts(
        @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String token
    ) {
        MyUser loggedInUser = null;
        if(token != null) {loggedInUser = userService.getByUsername(tokenService.getUsernameFromToken(token));}

        return postService.getAllPosts(loggedInUser);
    }

    @GetMapping("/recent")
    public List<Post> getRecentPosts() {
        return postService.getRecentPosts();
    }

    @PostMapping
    public Post createPost(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody Post newPost) 
        throws InvalidBearerTokenException, UserNotFoundException
    {

        MyUser user = userService.getByUsername(tokenService.getUsernameFromToken(token));

        return postService.createPost(newPost, user);
    }

    @GetMapping("/{userId}")
    public List<Post> getPostsByUserId(@PathVariable(name = "userId", required = true)UUID id) {
        return postService.getPostsByUserId(id);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<PostDTO>> getPaginatedPosts(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String token,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "userid", required = false) String userId
    ) 
        throws InvalidBearerTokenException,
            UserNotFoundException
    {
        MyUser loggedInUser = null;
        if(token != null) {loggedInUser = userService.getByUsername(tokenService.getUsernameFromToken(token));}

        if(userId != null) {
            Page<PostDTO> posts = postService.getPaginatedPostsByUserId(page, size, loggedInUser, UUID.fromString(userId));
            return ResponseEntity.ok(posts);
        }
        Page<PostDTO> posts = postService.getPaginatedPosts(page, size, loggedInUser);
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/like")
    public ResponseEntity<ToggleLikeResponse> toggleLike(@RequestBody LinkedHashMap<String, String> body) {

        UUID postId = UUID.fromString(body.get("postId"));
        UUID userId = UUID.fromString(body.get("userId"));

        return ResponseEntity.ok(postService.toggleLike(postId, userId));
    }       

}
