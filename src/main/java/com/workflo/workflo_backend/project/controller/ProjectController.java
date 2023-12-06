package com.workflo.workflo_backend.project.controller;


import com.workflo.workflo_backend.exceptions.CloudUploadException;
import com.workflo.workflo_backend.exceptions.UserNotFoundException;
import com.workflo.workflo_backend.exceptions.UserNotVerifiedException;
import com.workflo.workflo_backend.project.dtos.request.CreateProject;
import com.workflo.workflo_backend.project.dtos.response.ProjectResponse;
import com.workflo.workflo_backend.project.entities.ProjectCategory;
import com.workflo.workflo_backend.project.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("api/v1/user/project")
@AllArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProjectResponse> createProject(@RequestParam(value = "image", required = false) MultipartFile image,
                                                         @RequestParam(value = "userId") Long id,
                                                         @RequestParam(value = "project_name") String projectName,
                                                         @RequestParam(value = "summary") String summary,
                                                         @RequestParam(value = "description", required = false) String description,
                                                         @RequestParam(value = "category") ProjectCategory category,
                                                         @RequestParam(value = "skills", required = false) List<String> skills)
                                                                               throws UserNotFoundException,
                                                                                      UserNotVerifiedException,
                                                                                      CloudUploadException {
        CreateProject project = projectService.createProjectDTO(image, id, projectName, summary, description, category, skills);
        return ResponseEntity.status(201).body(projectService.createProject(project));
    }
    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@RequestParam(value = "userId") Long id,
                                                         @RequestParam(value = "project_name") String projectName,
                                                         @RequestParam(value = "summary") String summary,
                                                         @RequestParam(value = "description", required = false) String description,
                                                         @RequestParam(value = "category") ProjectCategory category,
                                                         @RequestParam(value = "skills", required = false) List<String> skills)
                                                                               throws UserNotFoundException,
                                                                                      UserNotVerifiedException,
                                                                                      CloudUploadException {
        CreateProject project = projectService.createProjectDTO(id, projectName, summary, description, category, skills);
        return ResponseEntity.status(201).body(projectService.createProject(project));
    }

}
