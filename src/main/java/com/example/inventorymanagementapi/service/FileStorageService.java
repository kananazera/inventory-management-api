package com.example.inventorymanagementapi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path uploadDir;
    private final String baseUrl;

    private static final List<String> ALLOWED_CONTENT_TYPES = List.of(
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_PNG_VALUE,
            "image/gif",
            "image/webp"
    );

    private static final List<String> ALLOWED_EXTENSIONS = List.of("jpg", "jpeg", "png", "gif", "webp");

    public FileStorageService(@Value("${file.upload-dir}") String uploadDir,
                              @Value("${file.base-url}") String baseUrl) throws IOException {
        this.uploadDir = Paths.get(uploadDir);
        this.baseUrl = baseUrl;

        if (!Files.exists(this.uploadDir)) {
            Files.createDirectories(this.uploadDir);
        }
    }

    public String storeFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_CONTENT_TYPES.contains(contentType.toLowerCase())) {
            throw new IllegalArgumentException("Only image files (JPG, PNG, GIF, WEBP) are allowed");
        }

        String originalFilename = file.getOriginalFilename();
        String extension = (originalFilename != null && originalFilename.contains("."))
                ? originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase()
                : "";

        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new IllegalArgumentException("Invalid file extension. Only images are allowed");
        }

        String filename = UUID.randomUUID() + "_" + originalFilename;

        Path targetPath = uploadDir.resolve(filename).normalize();
        file.transferTo(targetPath.toFile());

        return baseUrl + filename;
    }

    public String getUploadDir() {
        return uploadDir.toString();
    }
}
