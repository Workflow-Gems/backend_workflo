package com.workflo.workflo_backend.appConfig.services;

import com.workflo.workflo_backend.exceptions.CloudUploadException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudService {

    String upload(MultipartFile file) throws CloudUploadException;
}
