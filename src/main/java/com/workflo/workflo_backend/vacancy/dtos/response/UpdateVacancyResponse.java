package com.workflo.workflo_backend.vacancy.dtos.response;


import com.workflo.workflo_backend.vacancy.entity.VacancyStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateVacancyResponse {
    private String message;
    private VacancyStatus status;
}
