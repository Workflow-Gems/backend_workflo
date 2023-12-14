package com.workflo.workflo_backend.vacancy.dtos.request;


import com.workflo.workflo_backend.vacancy.entity.VacancyStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
public class UpdateVacancyRequest {
    private String text;
    private List<String> neededSkills;
    private VacancyStatus status;
}
