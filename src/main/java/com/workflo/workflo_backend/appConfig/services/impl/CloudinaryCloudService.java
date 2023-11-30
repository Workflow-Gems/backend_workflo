package com.workflo.workflo_backend.appConfig.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.workflo.workflo_backend.appConfig.services.CloudService;
import com.workflo.workflo_backend.exceptions.CloudUploadException;
import com.workflo.workflo_backend.exceptions.WorkFloException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;


@Service
@AllArgsConstructor
public class CloudinaryCloudService implements CloudService {
    private final Cloudinary cloudinary;
    @Override
    public String upload(MultipartFile file) throws CloudUploadException {
        try {
            Map<?, ?> result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return result.get("secure_url").toString();
        }catch (IOException e){
            throw new CloudUploadException(e.getMessage());
        }
    }
}
