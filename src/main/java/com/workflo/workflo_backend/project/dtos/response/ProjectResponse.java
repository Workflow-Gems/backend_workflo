package com.workflo.workflo_backend.project.dtos.response;


import com.workflo.workflo_backend.project.entities.ProjectCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponse {
    private Long id;
    private String name;
    private String summary;
    private String description;
    private ProjectCategory category;
}
