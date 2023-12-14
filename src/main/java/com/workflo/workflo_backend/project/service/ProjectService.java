package com.workflo.workflo_backend.project.service;

import com.workflo.workflo_backend.exceptions.*;
import com.workflo.workflo_backend.project.dtos.request.CreateProjectRequest;
import com.workflo.workflo_backend.project.dtos.response.ProjectProjection;
import com.workflo.workflo_backend.project.dtos.response.ProjectResponse;
import com.workflo.workflo_backend.project.entities.Project;
import com.workflo.workflo_backend.project.entities.ProjectCategory;
import com.workflo.workflo_backend.vacancy.dtos.request.VacancyRequest;
import com.workflo.workflo_backend.vacancy.dtos.response.VacancyResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProjectService {
    ProjectResponse createProject(CreateProjectRequest createProjectRequest) throws UserNotFoundException,
                                                                      CloudUploadException,
                                                                      UserNotVerifiedException, WorkFloException;
    default CreateProjectRequest createProjectDTO(MultipartFile image,
                                                  Long id,
                                                  String projectName,
                                                  String summary,
                                                  String description,
                                                  ProjectCategory category,
                                                  List<String> skills) {
        CreateProjectRequest project = new CreateProjectRequest();
        project.setCategory(category);
        project.setDescription(description);
        project.setName(projectName);
        project.setSummary(summary);
        project.setImage(image);
        project.setNeededSkills(skills);
        project.setUserCreatorId(id);
        return project;
    }
    String deleteProject(Long userId, Long projectId) throws UserNotFoundException, ProjectNotExistException, ProjectAndUserNotMatchException, WorkFloException;

    Project findProjectById(Long projectId) throws WorkFloException;

    Page<ProjectResponse> getAllProjects(int page, int size);

    ProjectProjection viewProjectById(long projectId) throws WorkFloException;

    List<ProjectResponse> viewCreatedProjectsByUser(Long userId) throws ProjectNotExistException;
}
