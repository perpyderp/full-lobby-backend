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
import com.perp.fulllobby.exception.CannotFindLikeException;
import com.perp.fulllobby.exception.CannotFindPostException;
import com.perp.fulllobby.model.Like;
import com.perp.fulllobby.model.MyUser;
import com.perp.fulllobby.model.Post;
import com.perp.fulllobby.model.ToggleLikeResponse;
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

    public List<PostDTO> getAllPosts(MyUser loggedInUser) {
        List<Post> posts = postRepository.findAll();
        List<PostDTO> postDTOs = new ArrayList<>();

        for(Post post : posts) {
            PostDTO postDTO = new PostDTO(post.getId(), post.getTitle(), post.getDescription(), null, false, post.getCreatedAt(), post.getUpdatedAt(), null);
            
            MyUser userPoster = post.getUser();
            UserPostDTO user = new UserPostDTO(userPoster.getId(), userPoster.getUsername(), userPoster.getAvatar());
            postDTO.setUser(user);

            if(loggedInUser != null) { 
                boolean isLiked = likeRepository.existsByUserIdAndPostId(loggedInUser.getId(), post.getId());
                postDTO.setLikedByMe(isLiked);
            }

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

    public Page<PostDTO> getPaginatedPosts(int page, int size, MyUser loggedInUser) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Post> posts = postRepository.findAll(pageable);
        Page<PostDTO> paginatedPostsDTO = posts.map(post -> {
                boolean likedByMe = false;
                if(loggedInUser != null) likedByMe = likeRepository.existsByUserIdAndPostId(loggedInUser.getId(), post.getId());
                return new PostDTO(post, likedByMe);
            }
        );

        return paginatedPostsDTO;
    }

    public ToggleLikeResponse toggleLike(UUID postId, UUID userId) {

        Post post = getPostById(postId);
        MyUser user = userService.getUserById(userId);
        
        Like existingLike = likeRepository.findByUserIdAndPostId(userId, postId);
        System.out.println(existingLike);
        if(existingLike == null) {
            Like like = new Like();
            like.setPost(post);
            like.setUser(user);

            likeRepository.save(like);

            return new ToggleLikeResponse(true);
        }

        Like like = likeRepository.findByUserIdAndPostId(userId, postId);
        likeRepository.delete(like);

        return new ToggleLikeResponse(false);

    }

    public Page<Post> getPostsBeforeCursor(Instant cursor, int pageSize) {
        Pageable pageable = PageRequest.of(0, pageSize, Sort.by("createdAt").descending());
        return postRepository.findPostsBeforeCursor(cursor, pageable);
    }

}
