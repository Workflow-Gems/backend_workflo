package com.workflo.workflo_backend.vacancy.dtos.request;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
public class VacancyRequest {
    @NotNull
    private Long projectIdentifier;
    @NotNull
    private Long userIdentifier;
    private List<String> neededSkills;
    private String text;
}
