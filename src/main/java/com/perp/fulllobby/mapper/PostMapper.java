package com.perp.fulllobby.mapper;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.perp.fulllobby.dto.LikesDTO;
import com.perp.fulllobby.dto.MyUserPostDTO;
import com.perp.fulllobby.dto.PostDTO;
import com.perp.fulllobby.model.Post;

public class PostMapper {
    
    private ModelMapper modelMapper = new ModelMapper();

    public PostDTO convertToPostDTO(Post post) {

        PostDTO postDTO = modelMapper.map(post, PostDTO.class);

        postDTO.setLikes(post.getLikes().stream()
            .map(like -> {
                LikesDTO likeDTO = new LikesDTO();
                likeDTO.setPostId(like.getPost().getId());
                likeDTO.setUserId(like.getUser().getId());
                likeDTO.setId(like.getId());
                return likeDTO;
        }).collect(Collectors.toList()));
        
        postDTO.setUser(new MyUserPostDTO(post.getUser().getId(), post.getUser().getUsername(), post.getUser().getAvatar()));

        return postDTO;

    }

}
