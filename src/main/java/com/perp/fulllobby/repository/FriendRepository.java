package com.perp.fulllobby.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import com.perp.fulllobby.model.Friendship;
import com.perp.fulllobby.model.MyUser;

public interface FriendRepository extends JpaRepository<Friendship, Long> {
    
    Optional<Friendship> findByFirstUserAndSecondUser(MyUser firstUser, MyUser secondUser);
}
