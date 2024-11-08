package com.agilemonkey.crm.service;

import com.agilemonkey.crm.dto.ImageDTO;
import com.agilemonkey.crm.entity.Image;
import com.agilemonkey.crm.entity.User;
import com.agilemonkey.crm.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Component
@PropertySource("classpath:image.properties")
public class FileImageService implements ImageService {
    private final String baseDirectory;
    private final ImageRepository imageRepository;

    public FileImageService(@Value("${image.base.directory}") String baseDirectory,
                            @Autowired ImageRepository imageRepository) {
        this.baseDirectory = baseDirectory;
        this.imageRepository = imageRepository;
    }

    @Override
    public ImageDTO save(MultipartFile imageFile, User user) throws IOException {
        String uniqueFileName = UUID.randomUUID().toString();

        Path filePath = saveFile(imageFile, uniqueFileName);

        createDatabaseRecord(imageFile, user, uniqueFileName, filePath);

        return ImageDTO.builder()
                .imageUrl(String.format("/image/%s", uniqueFileName))
                .build();
    }

    private void createDatabaseRecord(MultipartFile imageFile, User user, String uniqueFileName, Path filePath) {
        Image image = new Image();
        image.setName(imageFile.getOriginalFilename());
        image.setImageKey(uniqueFileName);
        image.setFileName(filePath.toString());
        image.setCreated(new Date());
        image.setCreatedBy(user);

        this.imageRepository.save(image);
    }

    private Path saveFile(MultipartFile imageFile, String uniqueFileName) throws IOException {
        Path uploadPath = Path.of(baseDirectory);
        Path filePath = uploadPath.resolve(uniqueFileName);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return filePath;
    }

    @Override
    public Optional<byte[]> load(String key) throws IOException {
        Image image = this.imageRepository.findImageByImageKey(key);
        if(image != null){
            return Optional.of(Files.readAllBytes(Path.of(image.getFileName())));
        }
        return Optional.empty();
    }
}
