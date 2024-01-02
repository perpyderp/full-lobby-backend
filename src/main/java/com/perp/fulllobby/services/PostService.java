package com.perp.fulllobby.services;

import java.util.ArrayList;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.perp.fulllobby.dto.LikesDTO;
import com.perp.fulllobby.dto.PostDTO;
import com.perp.fulllobby.dto.UserPostDTO;
import com.perp.fulllobby.exception.CannotCreatePostException;
import com.perp.fulllobby.exception.CannotFindPostException;
import com.perp.fulllobby.model.Like;
import com.perp.fulllobby.model.MyUser;
import com.perp.fulllobby.model.Post;
import com.perp.fulllobby.repository.LikeRepository;
import com.perp.fulllobby.repository.PostRepository;

@Service
public class PostService {
    
    private PostRepository postRepository;
    private LikeRepository likeRepository;
    private MyUserService userService;

    public PostService(PostRepository postRepository, LikeRepository likeRepository, MyUserService userService) {
        this.postRepository = postRepository;
        this.likeRepository = likeRepository;
        this.userService = userService;
    }

    public List<PostDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostDTO> postDTOs = new ArrayList<>();

        for(Post post : posts) {
            PostDTO postDTO = new PostDTO(post.getId(), post.getTitle(), post.getDescription(), null, null, post.getCreatedAt(), post.getUpdatedAt());

            MyUser userPoster = post.getUser();
            UserPostDTO user = new UserPostDTO(userPoster.getId(), userPoster.getUsername(), userPoster.getAvatar());
            postDTO.setUser(user);

            List<LikesDTO> likeDTOs = post.getLikes().stream()
                .map(like -> {
                    LikesDTO likeDTO = new LikesDTO();
                    likeDTO.setPostId(like.getPost().getId());
                    likeDTO.setUserId(like.getUser().getId());
                    likeDTO.setId(like.getId());
                    return likeDTO;
                }).collect(Collectors.toList());

            postDTO.setLikes(likeDTOs);
            postDTOs.add(postDTO);
        }

        return postDTOs;

    }

    // public List<Post> getPostsByUsername(String username) {
    //     return postRepository.findByUsername(username);
    // }

    public List<Post> getPostsByUserId(UUID id) {
        return postRepository.findByUserId(id);
    }

    public Post getPostById(UUID id) {
        return postRepository.findById(id).orElseThrow(CannotFindPostException::new);
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

    public Like likePost(UUID postId, UUID userId) {

        Post post = getPostById(postId);
        MyUser user = userService.getUserById(userId);

        System.out.println(post);
        Like like = new Like();
        like.setPost(post);
        like.setUser(user);

        return likeRepository.save(like);
    }

    public Page<Post> getPostsBeforeCursor(Instant cursor, int pageSize) {
        Pageable pageable = PageRequest.of(0, pageSize, Sort.by("createdAt").descending());
        return postRepository.findPostsBeforeCursor(cursor, pageable);
    }

}
