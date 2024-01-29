package com.perp.fulllobby.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.perp.fulllobby.mapper.PostMapper;
import com.perp.fulllobby.model.MyUser;
import com.perp.fulllobby.model.Post;

public class PostDTOTest {
    
    private PostMapper postMapper = new PostMapper();

    @Test
    public void whenConvertPostEntityToPostDTO_thenCorrect() {

        Post post = new Post(UUID.randomUUID(), "title", "description", new MyUser(), LocalDateTime.now(), LocalDateTime.now());

        PostDTO postDTO = postMapper.convertToPostDTO(post);

        assertEquals(post.getId(), postDTO.getId());
        assertEquals(post.getTitle(), postDTO.getTitle());
        assertEquals(post.getUser().getUsername(), postDTO.getUser().getUsername());


    }

}
