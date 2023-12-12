package com.workflo.workflo_backend.project.dtos.request;

import com.workflo.workflo_backend.project.entities.ProjectCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
public class CreateProjectRequest {
    private Long userCreatorId;
    private String name;
    private String summary;
    private String description;
    private ProjectCategory category;
    private List<String> neededSkills;
    private MultipartFile image;
}
