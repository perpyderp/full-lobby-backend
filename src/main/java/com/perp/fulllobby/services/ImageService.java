package com.perp.fulllobby.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.perp.fulllobby.model.Image;
import com.perp.fulllobby.repository.ImageRepository;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ImageService {
    
    private final ImageRepository imageRepository;

    // private static final String IMGUR_UPLOAD_URL = "https://api.imgur.com/3/image";
    private static final File DIRECTORY = new File(Dotenv.configure().load().get("IMG_URL"));
    private static final String URL = "http://localhost:8080/images/";

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public String uploadAvatar(MultipartFile file, String prefix) {
        try {
            String extensionType = "." + file.getContentType().split("/")[1];

            File img = File.createTempFile(prefix, extensionType, DIRECTORY);

            file.transferTo(img);

            String imageUrl = URL + img.getName();
            
            Image i = new Image(img.getName(), file.getContentType(), img.getPath(), imageUrl);

            Image saved = imageRepository.save(i);

            return "File uploaded successfully: " + img.getName();
        }
        catch(IOException e) {
            e.printStackTrace();
            return "File upload unsuccessful";
        }

    }

    public byte[] downloadImage(String filename) {
        try {
            Image image = imageRepository.findByImageName(filename).get();

            String filepath = image.getImagePath();

            byte[] imageBytes = Files.readAllBytes(new File(filepath).toPath());

            return imageBytes;
        }
        catch(IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getImageType(String filename) {
        Image image = imageRepository.findByImageName(filename).get();

        return image.getImageType();
    }

    // @TODO Store avatar images on imgur instead of locally
    public String uploadAvatar(MultipartFile avatar) {
        return "";
    }

}
