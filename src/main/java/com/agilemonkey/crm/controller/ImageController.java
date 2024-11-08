package com.agilemonkey.crm.controller;

import com.agilemonkey.crm.dto.ImageDTO;
import com.agilemonkey.crm.service.ImageService;
import com.agilemonkey.crm.security.AppUserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("image")
public class ImageController {
    private final ImageService imageManager;

    public ImageController(@Autowired ImageService imageManager) {
        this.imageManager = imageManager;
    }

    @PostMapping("/upload")
    public ResponseEntity<ImageDTO> upload(@RequestParam("image") MultipartFile imageFile,
                                           @AuthenticationPrincipal OAuth2User principal) throws IOException {
        return ResponseEntity.ok(imageManager.save(imageFile, ((AppUserPrincipal) principal).getUser()));
    }

    @GetMapping("image/{key}")
    public ResponseEntity<byte[]> load(@PathVariable("key")String key) throws IOException {
        return ResponseEntity.ok(imageManager.load(key).orElseThrow(() -> new IOException("File not found")));
    }
}
