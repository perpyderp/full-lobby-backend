package com.perp.fulllobby.dto;

import java.util.UUID;

public class LikesDTO {
    private UUID id;
    private UUID userId;
    private UUID postId;

    public LikesDTO() {}

    public LikesDTO(UUID id, UUID userId, UUID postId) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
    }
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public UUID getUserId() {
        return userId;
    }
    public void setUserId(UUID userId) {
        this.userId = userId;
    }
    public UUID getPostId() {
        return postId;
    }
    public void setPostId(UUID postId) {
        this.postId = postId;
    }
    @Override
    public String toString() {
        return "LikesDTO [id=" + id + ", userId=" + userId + ", postId=" + postId + "]";
    }

    
}
