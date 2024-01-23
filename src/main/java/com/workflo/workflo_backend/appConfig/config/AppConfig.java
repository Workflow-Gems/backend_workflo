package com.workflo.workflo_backend.appConfig.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.context.Context;

import javax.swing.plaf.PanelUI;

@Configuration
public class AppConfig {

    public static final String CLOUD_NAME = "cloud_name";
    public static final String API_KEY = "api_key";
    public static final String API_SECRET = "api_secret";
    public static final String SECURE = "secure";
    public static final String RESOURCE_TYPE = "resource_type";
    public static final String FOLDER = "folder";
    public static final String CLOUD_URL = "secure_url";

    public static final String MAIL_API_KEY = "api-key";

//    public static String BASEURL = "backendworkflo-production.up.railway.app";


    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
    @Bean public Context context(){ return new Context(); }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
