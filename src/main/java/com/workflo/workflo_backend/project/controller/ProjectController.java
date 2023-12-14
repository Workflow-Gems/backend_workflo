package com.workflo.workflo_backend.project.controller;


import com.workflo.workflo_backend.exceptions.ProjectNotExistException;
import com.workflo.workflo_backend.exceptions.WorkFloException;
import com.workflo.workflo_backend.project.dtos.request.CreateProjectRequest;
import com.workflo.workflo_backend.project.dtos.response.ProjectProjection;
import com.workflo.workflo_backend.project.dtos.response.ProjectResponse;
import com.workflo.workflo_backend.project.entities.ProjectCategory;
import com.workflo.workflo_backend.project.service.ProjectService;
import com.workflo.workflo_backend.vacancy.dtos.request.VacancyRequest;
import com.workflo.workflo_backend.vacancy.dtos.response.VacancyResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
                                                                                                            throws WorkFloException {
        CreateProjectRequest project = projectService.createProjectDTO(image, id, projectName, summary, description, category, skills);
        return ResponseEntity.status(201).body(projectService.createProject(project));
    }
    @DeleteMapping("/{userId}/{projectId}")
    public ResponseEntity<String> deleteProject(@PathVariable("userId") Long userId,
                                                @PathVariable("projectId") Long projectId) throws WorkFloException {
        return ResponseEntity.ok().body(projectService.deleteProject(userId, projectId));
    }
    @GetMapping("/{page}/{size}")
    public ResponseEntity<Page<ProjectResponse>> allProjects(@PathVariable int page, @PathVariable int size){
        return ResponseEntity.ok().body(projectService.getAllProjects(page, size));
    }
    @GetMapping
    public ResponseEntity<ProjectProjection> viewProject(@RequestParam(value = "id", required = true) Long id)
                                                                                            throws WorkFloException {
        return ResponseEntity.ok().body(projectService.viewProjectById(id));
    }
    @GetMapping("/{id}/getAllProjects")
    public ResponseEntity<List<ProjectResponse>> viewUserCreatedProject(@PathVariable Long id)
                                                                                      throws ProjectNotExistException {
        return ResponseEntity.ok().body(projectService.viewCreatedProjectsByUser(id));
    }
//    @PostMapping("/vacancy")
//    public ResponseEntity<VacancyResponse> createVacancy(@RequestBody VacancyRequest request) throws WorkFloException {
//        return ResponseEntity.ok().body(projectService.createVacancy(request));
//    }
}
