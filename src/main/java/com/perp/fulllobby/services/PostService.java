package com.perp.fulllobby.services;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.perp.fulllobby.exception.CannotCreatePostException;
import com.perp.fulllobby.exception.PostNotFoundException;
import com.perp.fulllobby.model.MyUser;
import com.perp.fulllobby.model.Post;
import com.perp.fulllobby.repository.PostRepository;

@Service
public class PostService {
    
    private PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // public List<Post> getPostsByUsername(String username) {
    //     return postRepository.findByUsername(username);
    // }

    public List<Post> getPostsByUserId(UUID id) {
        return postRepository.findByUserId(id);
    }

    public Post getPostById(UUID id) {
        return postRepository.findById(id).orElseThrow(PostNotFoundException::new);
    }

    public Post createPost(Post newPost, MyUser user) {
        Post post = new Post();

        post.setTitle(newPost.getTitle());
        post.setDescription(newPost.getDescription());
        post.setUser(user);

        try {
            return postRepository.save(post);
        }
        catch(Exception e) {
            throw new CannotCreatePostException();
        }
        
    }

    public List<Post> getRecentPosts() {
        return postRepository.findTop10ByOrderByCreatedAtDesc();
    }

    public Page<Post> getPaginatedPosts(int page, int size) {
        PageRequest pageable = PageRequest.of(0, 2);
        return postRepository.findAll(pageable);
    }

    public Page<Post> getPostsBeforeCursor(Instant cursor, int pageSize) {
        Pageable pageable = PageRequest.of(0, pageSize, Sort.by("createdAt").descending());
        return postRepository.findPostsBeforeCursor(cursor, pageable);
    }

}
