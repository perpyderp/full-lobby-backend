package com.perp.fulllobby.dto;

import java.util.UUID;

import com.perp.fulllobby.model.Image;

public class UserPostDTO {
    
    private UUID id;
    private String username;
    private Image avatar;

    public UserPostDTO() {}

    public UserPostDTO(UUID id, String username, Image avatar) {
        this.id = id;
        this.username = username;
        this.avatar = avatar;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Image getAvatar() {
        return avatar;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "UserPostDTO [id=" + id + ", username=" + username + ", avatar=" + avatar + "]";
    }

    
}
