package com.perp.fulllobby.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.perp.fulllobby.model.Friend;
import com.perp.fulllobby.model.MyUser;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    
    List<Friend> findByUserOrFriend(MyUser user, MyUser friend);
}
