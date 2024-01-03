package com.perp.fulllobby.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.perp.fulllobby.model.Like;

public interface LikeRepository extends JpaRepository<Like, UUID> {
    Like findByUserIdAndPostId(UUID userId, UUID postId);
    boolean existsByUserIdAndPostId(UUID userId, UUID postId);
}
