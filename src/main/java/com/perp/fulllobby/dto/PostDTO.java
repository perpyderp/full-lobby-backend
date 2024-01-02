package com.perp.fulllobby.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.perp.fulllobby.model.Post;

public class PostDTO {

    private UUID id;
    private String title;
    private String description;
    private List<LikesDTO> likes;
    private boolean likedByMe;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserPostDTO user;

    public PostDTO() {}

    public PostDTO(Post post, boolean likedByMe) {
        this.id = post.getId();
		this.title = post.getTitle();
		this.description = post.getDescription();
        this.likes = post.getLikes().stream()
            .map(like -> {
                LikesDTO likeDTO = new LikesDTO();
                likeDTO.setPostId(like.getPost().getId());
                likeDTO.setUserId(like.getUser().getId());
                likeDTO.setId(like.getId());
                return likeDTO;
            }).collect(Collectors.toList());
		this.likedByMe = likedByMe;
		this.createdAt = post.getCreatedAt();
		this.updatedAt = post.getUpdatedAt();
		this.user = new UserPostDTO(post.getUser().getId(), post.getUser().getUsername(), post.getUser().getAvatar());
    }

	public PostDTO(UUID id, String title, String description, List<LikesDTO> likes, boolean likedByMe,
			LocalDateTime createdAt, LocalDateTime updatedAt, UserPostDTO user) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.likes = likes;
		this.likedByMe = likedByMe;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.user = user;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<LikesDTO> getLikes() {
		return likes;
	}

	public void setLikes(List<LikesDTO> likes) {
		this.likes = likes;
	}

	public boolean isLikedByMe() {
		return likedByMe;
	}

	public void setLikedByMe(boolean likedByMe) {
		this.likedByMe = likedByMe;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public UserPostDTO getUser() {
		return user;
	}

	public void setUser(UserPostDTO user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "PostDTO [id=" + id + ", title=" + title + ", description=" + description + ", likes=" + likes
				+ ", likedByMe=" + likedByMe + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", user="
				+ user + "]";
	}

}
