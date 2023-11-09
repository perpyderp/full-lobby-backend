package com.perp.fulllobby.services;


import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.api.client.http.HttpResponse;
import com.perp.fulllobby.exception.CannotUpdateUserException;
import com.perp.fulllobby.exception.EmailAlreadyTakenException;
import com.perp.fulllobby.exception.IncorrectCredentialsException;
import com.perp.fulllobby.exception.UserNotFoundException;
import com.perp.fulllobby.model.MyUser;
import com.perp.fulllobby.model.RegistrationObject;
import com.perp.fulllobby.model.Role;
import com.perp.fulllobby.repository.RoleRepository;
import com.perp.fulllobby.repository.UserRepository;

@Service
public class MyUserService {
    
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public MyUserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public MyUser getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public List<MyUser> getAllUsers() {
        return userRepository.findAll();
    }

    public MyUser login(String username, String password) {
        MyUser user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);

        if(passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        else {
            throw new IncorrectCredentialsException();
        }
    }

    public MyUser registerUser(RegistrationObject newUser) {
        MyUser user = new MyUser();
        System.out.println(newUser);
        user.setUsername(newUser.getUsername());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.setEmail(newUser.getEmail());

        Set<Role> roles = user.getAuthorities();
        roles.add(roleRepository.findByAuthority("USER").get());
        user.setAuthorities(roles);

        try {
            return userRepository.save(user);
        }
        catch (Exception e) {
            throw new EmailAlreadyTakenException();
        }
    }

    public MyUser updatUser(MyUser updatedUser) {
        try {
            return userRepository.save(updatedUser);
        }
        catch (Exception e) {
            throw new CannotUpdateUserException();
        }
    }

    public List<MyUser> getUserFriends(Object d) {
        return null;
    }

    public ResponseEntity<HttpResponse> removeFriend(MyUser removeFriend) {
        return null;
    }

}
