package com.perp.fulllobby.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class PostDTO {

    private UUID id;
    private String title;
    private String description;
    private List<LikesDTO> likes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserPostDTO user;

    public PostDTO() {}

    public PostDTO(UUID id, String title, String description, UserPostDTO user, List<LikesDTO> likes, LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.user = user;
        this.likes = likes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public UserPostDTO getUser() {
        return user;
    }

    public void setUser(UserPostDTO user) {
        this.user = user;
    }

    public List<LikesDTO> getLikes() {
        return likes;
    }

    public void setLikes(List<LikesDTO> likes) {
        this.likes = likes;
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

    @Override
    public String toString() {
        return "PostDTO [id=" + id + ", title=" + title + ", description=" + description + ", likes=" + likes
                + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", user=" + user + "]";
    }

}
