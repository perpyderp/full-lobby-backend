package com.perp.fulllobby.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.perp.fulllobby.model.MyUser;

public interface UserRepository extends JpaRepository<MyUser, Long>{
    Optional<MyUser> findByUsername(String username);
}
