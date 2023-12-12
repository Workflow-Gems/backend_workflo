package com.workflo.workflo_backend.user.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;


@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter
public class UpdateUserRequest {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String about;
    private String jobTitle;
    private List<String> skills;
    private Map<String, String> portFolio;
    private String country;
    private String state;
    private String city;
    private Long zipCode;
}
