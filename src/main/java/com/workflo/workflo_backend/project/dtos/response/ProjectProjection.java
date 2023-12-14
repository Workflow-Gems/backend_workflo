package com.workflo.workflo_backend.project.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.workflo.workflo_backend.project.entities.ProjectCategory;
import com.workflo.workflo_backend.vacancy.entity.VacancyStatus;
import com.workflo.workflo_backend.user.dtos.response.FoundUserResponse;
import lombok.*;


@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectProjection {
    private String name;
    private String description;
    private ProjectCategory category;
    private FoundUserResponse owner;
    private VacancyStatus status;
}