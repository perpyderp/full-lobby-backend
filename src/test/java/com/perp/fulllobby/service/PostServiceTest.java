package com.perp.fulllobby.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.perp.fulllobby.dto.PostDTO;
import com.perp.fulllobby.model.MyUser;
import com.perp.fulllobby.model.Post;
import com.perp.fulllobby.repository.PostRepository;
import com.perp.fulllobby.services.PostService;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
    
    @InjectMocks
    PostService postService;

    @Mock
    PostRepository postRepository;

    @Test
    void getAllPosts() {
        Mockito.doReturn(getMockPosts(2)).when(postRepository).findAll();

        List<PostDTO> posts = this.postService.getAllPosts();

        assertEquals(2, posts.size());
    }

    private Iterable<Post> getMockPosts(int size) {
        List<Post> posts = new ArrayList<>(size);

        for(int i = 0; i < size; i++) {
            posts.add(new Post(UUID.randomUUID(), "title" + i, "description" + i, new MyUser(), LocalDateTime.now(), LocalDateTime.now()));
        }

        return posts;
    }

}
