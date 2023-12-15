package com.workflo.workflo_backend.join_project.dtos.request;


import com.workflo.workflo_backend.join_project.models.JoinProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
public class ViewVacancyRequest {
    private Long userId, vacancyId;
    private JoinProjectStatus status;
}
