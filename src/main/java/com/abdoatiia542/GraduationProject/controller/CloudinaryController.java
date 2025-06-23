package com.abdoatiia542.GraduationProject.controller;

import com.abdoatiia542.GraduationProject.cloudnairy.CloudinaryService;
import com.abdoatiia542.GraduationProject.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/cloudinary")
public class CloudinaryController {

    private final CloudinaryService cloudinaryService;

    @Autowired
    public CloudinaryController(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body(ApiResponse.failure("File is empty"));
            }
            Map result = cloudinaryService.upload(file, null);
            return ResponseEntity.ok(ApiResponse.success("File uploaded successfully", result));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.failure("File upload failed: " + e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{publicId}")
    public ResponseEntity<ApiResponse> deleteFile(@PathVariable String publicId) {
        try {
            if (publicId == null || publicId.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(ApiResponse.failure("Invalid file identifier"));
            }
            Map result = cloudinaryService.delete(publicId);
            if ("ok".equals(result.get("result"))) {
                return ResponseEntity.ok(ApiResponse.success("File deleted successfully", result));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ApiResponse.failure("File deletion failed: " + result.get("error")));
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.failure("File deletion failed: " + e.getMessage()));
        }
    }

    @GetMapping("/url/{publicId}")
    public ResponseEntity<ApiResponse> getUrl(@PathVariable String publicId) {
        if (publicId == null || publicId.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(ApiResponse.failure("Invalid file identifier"));
        }
        String url = cloudinaryService.getUrl(publicId);
        return ResponseEntity.ok(ApiResponse.success("File URL retrieved successfully", url));
    }

    @GetMapping("/download/{publicId}")
    public ResponseEntity<ApiResponse> downloadFile(@PathVariable String publicId) {
        if (publicId == null || publicId.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(ApiResponse.failure("Invalid file identifier"));
        }
        String url = cloudinaryService.getUrl(publicId);
        return ResponseEntity.ok(ApiResponse.success("Download link", url));
    }
}