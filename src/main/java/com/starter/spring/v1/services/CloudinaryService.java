package com.starter.spring.v1.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {
    @Value("${cloudinary.cloud_name}")
    private String CLOUD_NAME;

    @Value("${cloudinary.api_key}")
    private String CLOUDINARY_API_KEY;

    @Value("${cloudinary.api_secret}")
    private String CLOUD_API_SECRET;

    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", CLOUD_NAME,
            "api_key", CLOUDINARY_API_KEY,
            "api_secret", CLOUD_API_SECRET,
            "secure", true
    ));

    public Map uploadImage(String imageStr) throws IOException {
        return cloudinary.uploader().upload(imageStr, ObjectUtils.emptyMap());
    }


}
