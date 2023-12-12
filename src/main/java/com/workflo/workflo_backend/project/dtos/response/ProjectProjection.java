package com.workflo.workflo_backend.project.dtos.response;

import com.workflo.workflo_backend.project.entities.ProjectCategory;
import com.workflo.workflo_backend.project.vacancy.entity.VacancyStatus;
import com.workflo.workflo_backend.user.dtos.response.FoundUserResponse;
import lombok.*;


@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
public class ProjectProjection {
    private String name;
    private String description;
    private ProjectCategory category;
    private FoundUserResponse owner;
    private VacancyStatus status;
}