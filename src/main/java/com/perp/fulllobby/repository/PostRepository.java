package com.perp.fulllobby.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.perp.fulllobby.model.Post;

public interface PostRepository extends JpaRepository<Post, Long>{
    List<Post> findByUserId(long userId);
}
