package com.perp.fulllobby.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.perp.fulllobby.exception.ImageNotFoundException;
import com.perp.fulllobby.exception.UnableToSaveAvatarException;
import com.perp.fulllobby.exception.UnableToSaveBannerException;
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
    private static final String URL = "http://localhost:8080/api/images/";

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image uploadAvatar(MultipartFile file) throws UnableToSaveAvatarException {
        try {
            String extensionType = "." + file.getContentType().split("/")[1];

            File img = File.createTempFile("avatar", extensionType, DIRECTORY);

            file.transferTo(img);

            String imageUrl = URL + img.getName();
            
            Image i = new Image(img.getName(), file.getContentType(), img.getPath(), imageUrl);

            Image saved = imageRepository.save(i);

            return saved;
        }
        catch(IOException e) {
            throw new UnableToSaveAvatarException();
        }

    }

    public Image uploadBanner(MultipartFile file) throws UnableToSaveBannerException {
        try {
            String extensionType = "." + file.getContentType().split("/")[1];

            File img = File.createTempFile("banner", extensionType, DIRECTORY);

            file.transferTo(img);

            String imageUrl = URL + img.getName();
            
            Image i = new Image(img.getName(), file.getContentType(), img.getPath(), imageUrl);

            Image saved = imageRepository.save(i);

            return saved;
        }
        catch(IOException e) {
            throw new UnableToSaveBannerException();
        }

    }

    public byte[] downloadImage(String filename) throws ImageNotFoundException{
        try {
            Image image = imageRepository.findByImageName(filename).get();

            String filepath = image.getImagePath();

            byte[] imageBytes = Files.readAllBytes(new File(filepath).toPath());

            return imageBytes;
        }
        catch(IOException e) {
            throw new ImageNotFoundException(filename);
        }
    }

    public String getImageType(String filename) {
        Image image = imageRepository.findByImageName(filename).get();

        return image.getImageType();
    }

    // @TODO Store avatar images on imgur instead of locally
    // public String uploadAvatar(MultipartFile avatar) {
    //     return "";
    // }

}
