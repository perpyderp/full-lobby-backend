package com.perp.fulllobby.services;

import java.io.File;

import org.springframework.stereotype.Service;

import com.perp.fulllobby.repository.ImageRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ImageService {
    
    private final ImageRepository imageRepository;

    private static final String URL = "https://api.imgur.com/3/image";

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

}
