package com.perp.fulllobby.mapper;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.perp.fulllobby.dto.MyUserDTO;
import com.perp.fulllobby.model.MyUser;

public class MyUserMapper {

    private ModelMapper modelMapper = new ModelMapper();

    public MyUserDTO convertToMyUserDTO(MyUser user) {

        MyUserDTO userDTO = modelMapper.map(user, MyUserDTO.class);

        userDTO.setFriends(user.getFriends().stream()
            .map(friend -> {
                MyUserDTO friendDTO = modelMapper.map(friend, MyUserDTO.class);
                return friendDTO;
        }).collect(Collectors.toList()));

        return userDTO;

    }

    public MyUser convertToMyUser(MyUserDTO userDTO) {

        MyUser user = modelMapper.map(userDTO, MyUser.class);

        userDTO.getFriends().stream()
            .map(friendDTO -> {
                MyUser friend = modelMapper.map(friendDTO, MyUser.class);
                return friend;
        }).collect(Collectors.toSet());

        return user;

    }
}
