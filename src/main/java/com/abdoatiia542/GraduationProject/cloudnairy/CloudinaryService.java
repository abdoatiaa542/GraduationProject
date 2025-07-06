package com.abdoatiia542.GraduationProject.cloudnairy;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(@Value("${cloudinary.cloud-name}") String cloudName,
                             @Value("${cloudinary.api-key}") String apiKey,
                             @Value("${cloudinary.api-secret}") String apiSecret) {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret));
    }

    public Map upload(MultipartFile file) throws IOException {
        return cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.asMap("resource_type", "auto")
        );
    }

    public Map delete(String publicId) throws IOException {
        Map<String, Object> options = ObjectUtils.asMap("invalidate", true);
        return cloudinary.uploader().destroy(publicId, options);
    }

    public String getUrl(String publicId) {
        return cloudinary.url().generate(publicId);
    }
}