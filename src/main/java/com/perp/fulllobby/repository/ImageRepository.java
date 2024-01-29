package com.perp.fulllobby.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.perp.fulllobby.model.Image;

public interface ImageRepository extends JpaRepository<Image, UUID>{
    
    Optional<Image> findByImageName(String imageName);

}
