package com.workflo.workflo_backend.vacancy.dtos.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
public class VacancyRequest {
    private Long projectIdentifier;
    private Long userIdentifier;
    private List<String> neededSkills;
    private String text;
}
