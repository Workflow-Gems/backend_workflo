package com.workflo.workflo_backend.user.dtos.response;

import lombok.*;

import java.util.List;
import java.util.Map;


@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
public class ProfileResponse {
    private String image;
    private String about;
    private String jobTitle;
    private List<String> skills;
    private Map<String, String> portFolio;
}
