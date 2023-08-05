package com.perp.fulllobby.services;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.perp.fulllobby.model.MyUser;
import com.perp.fulllobby.model.Role;
import com.perp.fulllobby.repository.RoleRepository;
import com.perp.fulllobby.repository.UserRepository;

@Service
public class UserService {
    
    private final UserRepository userRepo;

    private final RoleRepository roleRepo;

    public UserService(UserRepository userRepo, RoleRepository roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    public MyUser registerUser(MyUser user) {
        Set<Role> roles = user.getAuthorities();
        roles.add(roleRepo.findByAuthority("USER").get());
        user.setAuthorities(roles);

        return userRepo.save(user);
    }


}
