package com.perp.fulllobby.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perp.fulllobby.model.MyUser;


@Repository
public interface UserRepository extends JpaRepository<MyUser, Long>{
    public MyUser findByUsername(String username);
}
