package com.perp.fulllobby.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.perp.fulllobby.model.Post;

public interface PostRepository extends JpaRepository<Post, UUID>{
    List<Post> findByUserId(UUID userId);
    List<Post> findTop10ByOrderByCreatedAtDesc();
}
