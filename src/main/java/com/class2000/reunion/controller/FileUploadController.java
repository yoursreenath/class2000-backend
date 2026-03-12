package com.class2000.reunion.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
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

    // ── Cloudinary config (injected from env vars / application.properties) ──
    @Value("${cloudinary.cloud-name:}")
    private String cloudName;

    @Value("${cloudinary.api-key:}")
    private String apiKey;

    @Value("${cloudinary.api-secret:}")
    private String apiSecret;

    private Cloudinary cloudinary;
    private boolean useCloudinary = false;

    // ── Local fallback (dev only) ──
    private static final Path UPLOAD_DIR = Paths.get(
        System.getProperty("user.home"), "class2000-uploads"
    ).toAbsolutePath().normalize();

    @PostConstruct
    public void init() {
        // Use Cloudinary if all three credentials are provided
        if (!cloudName.isBlank() && !apiKey.isBlank() && !apiSecret.isBlank()) {
            cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key",    apiKey,
                "api_secret", apiSecret,
                "secure",     true
            ));
            useCloudinary = true;
            System.out.println("=== File storage: Cloudinary (" + cloudName + ") ===");
        } else {
            // Local disk fallback for dev
            try {
                Files.createDirectories(UPLOAD_DIR);
                System.out.println("=== File storage: Local disk → " + UPLOAD_DIR + " ===");
            } catch (IOException e) {
                System.err.println("Failed to create upload dir: " + e.getMessage());
            }
        }
    }

    // ── Upload endpoint ───────────────────────────────────────────────────────
    @PostMapping("/photo")
    public ResponseEntity<?> uploadPhoto(@RequestParam("file") MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Only image files are allowed."));
        }

        try {
            if (useCloudinary) {
                return uploadToCloudinary(file);
            } else {
                return uploadToLocalDisk(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Upload failed: " + e.getMessage()));
        }
    }

    @SuppressWarnings("unchecked")
    private ResponseEntity<?> uploadToCloudinary(MultipartFile file) throws IOException {
        Map<String, Object> result = cloudinary.uploader().upload(
            file.getBytes(),
            ObjectUtils.asMap(
                "folder",          "class2000",
                "resource_type",   "image",
                "use_filename",    false,
                "unique_filename", true
            )
        );
        String secureUrl = (String) result.get("secure_url");
        System.out.println("Cloudinary upload OK: " + secureUrl);
        return ResponseEntity.ok(Map.of("url", secureUrl));
    }

    private ResponseEntity<?> uploadToLocalDisk(MultipartFile file) throws IOException {
        String original  = file.getOriginalFilename();
        String extension = (original != null && original.contains("."))
            ? original.substring(original.lastIndexOf("."))
            : "";
        String filename = UUID.randomUUID() + extension;
        Path   filePath = UPLOAD_DIR.resolve(filename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("Local upload OK: " + filePath);
        return ResponseEntity.ok(Map.of(
            "url",      "/api/upload/files/" + filename,
            "filename", filename
        ));
    }

    // ── Serve local files (dev only — not used when Cloudinary is active) ────
    @GetMapping("/files/{filename}")
    public ResponseEntity<byte[]> serveFile(@PathVariable String filename) {
        try {
            Path filePath = UPLOAD_DIR.resolve(filename).normalize();
            if (!filePath.startsWith(UPLOAD_DIR)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            if (!Files.exists(filePath)) {
                return ResponseEntity.notFound().build();
            }
            byte[] bytes = Files.readAllBytes(filePath);
            String type  = Files.probeContentType(filePath);
            if (type == null) type = "application/octet-stream";
            return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(type))
                .header(HttpHeaders.CACHE_CONTROL, "no-cache")
                .body(bytes);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
