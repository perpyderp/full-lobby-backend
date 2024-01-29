package com.perp.fulllobby.dto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.perp.fulllobby.model.Image;

public class MyUserDTO {
    
    private UUID id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Date dob;
    private String bio;
    private String nickname;
    private Image avatar;
    private Image banner;
    private Boolean verified;
    private List<MyUserDTO> friends;
    
    public MyUserDTO() {}
    
    public MyUserDTO(UUID id, String username, String email, String firstName, String lastName, Date dob, String bio,
            String nickname, Image avatar, Image banner, Boolean verified, List<MyUserDTO> friends) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.bio = bio;
        this.nickname = nickname;
        this.avatar = avatar;
        this.banner = banner;
        this.verified = verified;
        this.friends = friends;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Image getAvatar() {
        return avatar;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }

    public Image getBanner() {
        return banner;
    }

    public void setBanner(Image banner) {
        this.banner = banner;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public List<MyUserDTO> getFriends() {
        return friends;
    }

    public void setFriends(List<MyUserDTO> friends) {
        this.friends = friends;
    }



}
