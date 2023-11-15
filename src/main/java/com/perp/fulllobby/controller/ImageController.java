package com.perp.fulllobby.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perp.fulllobby.exception.ImageNotFoundException;
import com.perp.fulllobby.services.ImageService;

@RestController
@RequestMapping("/api/images")
@CrossOrigin("*")
public class ImageController {
    
    public final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @ExceptionHandler({ImageNotFoundException.class})
    public ResponseEntity<String> handleImageNotFoundException() {
        return new ResponseEntity<String>("Couldn't find the image", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{filename}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable String filename) throws ImageNotFoundException{
        byte[] imageBytes = imageService.downloadImage(filename);

        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.valueOf(imageService.getImageType(filename)))
            .body(imageBytes);
    }

}
