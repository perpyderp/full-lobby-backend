package com.perp.fulllobby.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import com.perp.fulllobby.model.Friend;
import com.perp.fulllobby.model.MyUser;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    
    Optional<Friend> findByUserAndFriend(MyUser user, MyUser friend);
}
