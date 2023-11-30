package com.workflo.workflo_backend.user.dtos.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
public class ProfileRequest {
    private Long userId;
    private String about;
    private String jobTitle;
    private Map<String, String> portfolio;
    private List<String> skills;
    private MultipartFile image;
}
