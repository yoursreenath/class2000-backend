package com.class2000.reunion.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload")
public class FileUploadController {

    // Always use a fixed absolute path — no relative path ambiguity
    private static final Path UPLOAD_DIR = Paths.get(
        System.getProperty("user.home"), "class2000-uploads"
    ).toAbsolutePath().normalize();

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(UPLOAD_DIR);
            System.out.println("==============================================");
            System.out.println("Upload directory: " + UPLOAD_DIR);
            System.out.println("Exists: " + Files.exists(UPLOAD_DIR));
            System.out.println("==============================================");
        } catch (IOException e) {
            System.err.println("Failed to create upload directory: " + e.getMessage());
        }
    }

    @PostMapping("/photo")
    public ResponseEntity<?> uploadPhoto(@RequestParam("file") MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Only image files are allowed."));
        }

        try {
            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String uniqueFilename = UUID.randomUUID().toString() + extension;
            Path filePath = UPLOAD_DIR.resolve(uniqueFilename);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Saved: " + filePath + " (" + Files.size(filePath) + " bytes)");

            return ResponseEntity.ok(Map.of(
                "url", "/api/upload/files/" + uniqueFilename,
                "filename", uniqueFilename
            ));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Failed to store file: " + e.getMessage()));
        }
    }

    @GetMapping("/files/{filename}")
    public ResponseEntity<byte[]> serveFile(@PathVariable String filename) {
        try {
            // Resolve against the same fixed absolute UPLOAD_DIR
            Path filePath = UPLOAD_DIR.resolve(filename).normalize();

            System.out.println("Serve request  : " + filename);
            System.out.println("Resolved path  : " + filePath);
            System.out.println("Starts with dir: " + filePath.startsWith(UPLOAD_DIR));
            System.out.println("File exists    : " + Files.exists(filePath));

            // Security: reject path traversal attempts
            if (!filePath.startsWith(UPLOAD_DIR)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            if (!Files.exists(filePath)) {
                return ResponseEntity.notFound().build();
            }

            byte[] bytes = Files.readAllBytes(filePath);
            String type = Files.probeContentType(filePath);
            if (type == null) type = "application/octet-stream";

            return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(type))
                .header(HttpHeaders.CACHE_CONTROL, "no-cache")
                .body(bytes);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}
