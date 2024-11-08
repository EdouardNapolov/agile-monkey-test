package com.agilemonkey.crm.service;

import com.agilemonkey.crm.dto.ImageDTO;
import com.agilemonkey.crm.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

/**
 * Interface to the class that manages image save and load.
 */
public interface ImageService {

    /**
     * Saves uploaded image and returns URL to it.
     *
     * @param imageFile the uploaded image to save
     * @param user
     * @return the URL to the image to retrieve
     * @throws IOException
     */
    ImageDTO save(MultipartFile imageFile, User user) throws IOException;

    /**
     * Retrieves image by image key.
     * @param key the key that identifies the image
     * @return the retrieved image as byte array
     * @throws IOException
     */
    Optional<byte[]> load(String key) throws IOException;
}
