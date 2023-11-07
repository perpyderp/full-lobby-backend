package com.perp.fulllobby.services;

import org.springframework.stereotype.Service;

import com.perp.fulllobby.exception.CannotCreatePostException;
import com.perp.fulllobby.exception.PostNotFoundException;
import com.perp.fulllobby.model.Post;
import com.perp.fulllobby.repository.PostRepository;

@Service
public class PostService {
    
    private PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id).orElseThrow(PostNotFoundException::new);
    }

    public Post createPost(Post newPost) {
        Post post = new Post();

        post.setTitle(newPost.getTitle());
        post.setDescription(newPost.getDescription());

        try {
            return postRepository.save(post);
        }
        catch(Exception e) {
            throw new CannotCreatePostException();
        }
        
    }

}
