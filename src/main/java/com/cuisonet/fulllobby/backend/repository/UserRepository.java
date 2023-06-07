package com.cuisonet.fulllobby.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cuisonet.fulllobby.backend.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
}
