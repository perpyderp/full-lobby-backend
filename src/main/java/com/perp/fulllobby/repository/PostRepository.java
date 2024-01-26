package com.perp.fulllobby.repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.perp.fulllobby.model.Post;

public interface PostRepository extends JpaRepository<Post, UUID>{
    List<Post> findByUserId(UUID userId);
    List<Post> findTop10ByOrderByCreatedAtDesc();
    Page<Post> findAll(Pageable pageable);
    Page<Post> findByUserId(UUID userId, Pageable pageable);
    
    @Query("SELECT p FROM Post p WHERE p.createdAt < :cursor ORDER BY p.createdAt DESC")
    Page<Post> findPostsBeforeCursor(
        @Param("cursor") Instant cursor, // Assuming createdAt is of type Instant or Timestamp
        Pageable pageable
    );
}
