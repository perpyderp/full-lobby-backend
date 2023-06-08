package com.perp.fulllobby.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.perp.fulllobby.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
}
