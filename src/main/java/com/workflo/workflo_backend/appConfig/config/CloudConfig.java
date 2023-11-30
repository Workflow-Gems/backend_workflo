package com.workflo.workflo_backend.appConfig.config;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class CloudConfig {


    @Value("${cloudinary.cloud.name}")
    private String cloudName;
    @Value("${cloudinary.cloud.api.key}")
    private String apiKey;
    @Value("${cloudinary.cloud.api.secret_key}")
    private String apiSecret;
    @Value("${cloudinary.cloud.resource_type}")
    private String resourceType;
    @Value("${cloudinary.cloud.folder_name}")
    private String folderName;

    @Bean
    public Cloudinary cloudinary(){
        Map<?, ?> param = ObjectUtils.asMap(
                "cloud_name", cloudName,
                        "api_key", apiKey,
                        "api_secret", apiSecret,
                        "secure", true,
                        "resource_type", resourceType,
                        "folder", folderName
        );
        return new Cloudinary(param);
    }
}
