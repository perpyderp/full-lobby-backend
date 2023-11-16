package com.perp.fulllobby.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.perp.fulllobby.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long>{
    
    Optional<Image> findByImageName(String imageName);

}
