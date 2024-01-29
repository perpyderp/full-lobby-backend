package com.perp.fulllobby.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.perp.fulllobby.mapper.MyUserMapper;
import com.perp.fulllobby.model.Image;
import com.perp.fulllobby.model.MyUser;

public class MyUserDTOTest {
    
    private MyUserMapper userMapper = new MyUserMapper();

    @Test
    public void whenConvertMyUserToMyUsertDTO_thenCorrect() {

        MyUser user = new MyUser(
            UUID.randomUUID(),
            "perpetual",
            "test@email.com",
            "password",
            "Jacob",
            "Cui",
            new Date(),
            "Welcome to my page!",
            "perp",
            new Image(
                UUID.randomUUID(),
                "avatar",
                "png",
                "/img/avatar5909254479919301460.png",
                "http://localhost:8080/api/images/avatar5909254479919301460.png"
            ),
            new Image(
                UUID.randomUUID(),
                "avatar5909254479919301460",
                "png",
                "/img/avatar5909254479919301460.png",
                "http://localhost:8080/api/images/avatar5909254479919301460.png"
            ),
            new HashSet<MyUser>()
        );

        MyUserDTO userDTO = userMapper.convertToMyUserDTO(user);

        assertEquals(user.getFriends().size(), userDTO.getFriends().size());
        assertEquals(user.getUsername(), userDTO.getUsername());
        assertEquals(user.getAvatar(), userDTO.getAvatar());
        assertEquals(user.getFirstName(), userDTO.getFirstName());
        assertEquals(user.getLastName(), userDTO.getLastName());

    }

    @Test
    public void whenConvertMyUserDTOToMyUser_thenCorrect() {

        MyUserDTO userDTO = new MyUserDTO(
            UUID.randomUUID(),
            "perpetual",
            "test@email.com",
            "Jacob",
            "Cui",
            new Date(),
            "Welcome to my page!",
            "perp", new Image(), new Image(),
            false,
            new ArrayList<MyUserDTO>()
        );

        userDTO.getFriends().add(new MyUserDTO(null, null, null, null, null, null, null, null, null, null, null, null));

        MyUser user = userMapper.convertToMyUser(userDTO);

        assertEquals(userDTO.getId(), user.getId());
        assertEquals(userDTO.getFriends().get(0).getUsername(), user.getFriends().iterator().next().getUsername());
        assertEquals(userDTO.getUsername(), user.getUsername());
        assertEquals(userDTO.getEmail(), user.getEmail());
        assertEquals(userDTO.getFirstName(), user.getFirstName());
        assertEquals(userDTO.getLastName(), user.getLastName());
        assertEquals(userDTO.getDob(), user.getDob());
        assertEquals(userDTO.getBio(), user.getBio());
        assertEquals(userDTO.getNickname(), user.getNickname());
        assertEquals(userDTO.getAvatar(), user.getAvatar());
        assertEquals(userDTO.getBanner(), user.getBanner());
        assertEquals(userDTO.getVerified(), user.getVerified());
        
    }

}
