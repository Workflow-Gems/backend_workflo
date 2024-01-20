package com.workflo.workflo_backend.appConfig.config;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.workflo.workflo_backend.appConfig.config.AppConfig.MAIL_API_KEY;
import static org.springframework.http.MediaType.APPLICATION_JSON;


@Configuration
@Getter
public class MailConfig {

    @Value("${brevo.mail.api.key}")
    private String apiKey;
    @Value("${brevo.mail.api.url}")
    private String url;

    //Headers
    @Bean
    public HttpHeaders httpHeaders(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(MAIL_API_KEY, apiKey);
        httpHeaders.setAccept(List.of(APPLICATION_JSON));
        httpHeaders.setContentType(APPLICATION_JSON);
        return httpHeaders;
    }

    //temp[late to post
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
