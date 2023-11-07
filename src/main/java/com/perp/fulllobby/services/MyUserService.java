package com.perp.fulllobby.services;


import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.perp.fulllobby.exception.CannotUpdateUserException;
import com.perp.fulllobby.exception.EmailAlreadyTakenException;
import com.perp.fulllobby.exception.UserNotFoundException;
import com.perp.fulllobby.model.MyUser;
import com.perp.fulllobby.model.Role;
import com.perp.fulllobby.repository.RoleRepository;
import com.perp.fulllobby.repository.UserRepository;

@Service
public class MyUserService {
    
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public MyUserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public MyUser getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public List<MyUser> getAllUsers() {
        return userRepository.findAll();
    }

    public MyUser registerUser(MyUser newUser) {
        MyUser user = new MyUser();
        user.setUsername(newUser.getUsername());
        user.setFirstName(newUser.getFirstName());
        user.setPassword(newUser.getPassword());
        user.setLastName(newUser.getLastName());
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

}
