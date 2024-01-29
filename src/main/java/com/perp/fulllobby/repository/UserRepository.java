package com.perp.fulllobby.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.perp.fulllobby.model.MyUser;

public interface UserRepository extends JpaRepository<MyUser, UUID> {
    Optional<MyUser> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
