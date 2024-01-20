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

import static com.workflo.workflo_backend.appConfig.config.AppConfig.CLOUD_URL;


@Service
@AllArgsConstructor
public class CloudinaryCloudService implements CloudService {
    private final Cloudinary cloudinary;
    @Override
    public String upload(MultipartFile file) throws CloudUploadException {
        try {
            Map<?, ?> result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return result.get(CLOUD_URL).toString();
        }catch (IOException e){
            throw new CloudUploadException(e.getMessage());
        }
    }
}
