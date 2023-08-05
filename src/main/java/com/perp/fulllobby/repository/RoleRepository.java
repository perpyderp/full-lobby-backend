package com.perp.fulllobby.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.perp.fulllobby.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{
    Optional<Role> findByAuthority(String authority);
}
