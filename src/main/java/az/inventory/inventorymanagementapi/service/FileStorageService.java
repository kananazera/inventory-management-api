package az.inventory.inventorymanagementapi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path uploadDir;
    private final String baseUrl;

    private static final List<String> ALLOWED_IMAGE_TYPES = List.of(
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_PNG_VALUE,
            "image/gif",
            "image/webp"
    );

    private static final List<String> ALLOWED_IMAGE_EXTENSIONS = List.of("jpg", "jpeg", "png", "gif", "webp");

    private static final List<String> ALLOWED_DOCUMENT_EXTENSIONS = List.of("pdf", "docx");

    public FileStorageService(@Value("${file.upload-dir}") String uploadDir,
                              @Value("${file.base-url}") String baseUrl) throws IOException {
        this.uploadDir = Paths.get(uploadDir);
        this.baseUrl = baseUrl;

        if (!Files.exists(this.uploadDir)) {
            Files.createDirectories(this.uploadDir);
        }
    }

    public String storeImageFile(String subFolder, MultipartFile file) throws IOException {
        validateImage(file);
        return storeFileInternal(subFolder, file);
    }

    public String storeDocumentFile(String subFolder, MultipartFile file) throws IOException {
        validateDocument(file);
        return storeFileInternal(subFolder, file);
    }

    public String storeImageFile(MultipartFile file) throws IOException {
        return storeImageFile("", file);
    }

    public String storeDocumentFile(MultipartFile file) throws IOException {
        return storeDocumentFile("", file);
    }

    private String storeFileInternal(String subFolder, MultipartFile file) throws IOException {
        String originalFilename = Paths.get(file.getOriginalFilename()).getFileName().toString();
        if (originalFilename == null || originalFilename.isBlank()) {
            throw new IllegalArgumentException("File name is invalid");
        }

        String filename = UUID.randomUUID() + "_" + originalFilename;
        Path targetDir = uploadDir.resolve(subFolder).normalize();
        Files.createDirectories(targetDir);

        Path targetPath = targetDir.resolve(filename).normalize();
        file.transferTo(targetPath.toFile());

        String filePath = Paths.get(subFolder, filename).toString().replace("\\", "/");
        return baseUrl.endsWith("/") ? baseUrl + filePath : baseUrl + "/" + filePath;
    }

    public void deleteFile(String fileUrl) {
        if (fileUrl == null || fileUrl.isBlank()) return;

        try {
            String relativePath = fileUrl.replace(baseUrl, "");
            Path filePath = uploadDir.resolve(relativePath).normalize();
            Files.deleteIfExists(filePath);
        } catch (Exception e) {
            System.err.println("Failed to delete file: " + fileUrl + " due to " + e.getMessage());
        }
    }

    private void validateImage(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_IMAGE_TYPES.contains(contentType.toLowerCase())) {
            throw new IllegalArgumentException("Only image files (JPG, PNG, GIF, WEBP) are allowed");
        }

        String extension = getExtension(file);
        if (!ALLOWED_IMAGE_EXTENSIONS.contains(extension)) {
            throw new IllegalArgumentException("Invalid image extension");
        }
    }

    private void validateDocument(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        String extension = getExtension(file);
        if (!ALLOWED_DOCUMENT_EXTENSIONS.contains(extension)) {
            throw new IllegalArgumentException("Only PDF and DOCX files are allowed");
        }
    }

    private String getExtension(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !originalFilename.contains(".")) {
            return "";
        }
        return originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
    }

    public String getUploadDir() {
        return uploadDir.toString();
    }
}