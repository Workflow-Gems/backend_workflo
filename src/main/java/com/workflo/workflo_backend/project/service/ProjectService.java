package com.workflo.workflo_backend.project.service;

import com.workflo.workflo_backend.exceptions.*;
import com.workflo.workflo_backend.project.dtos.request.CreateProject;
import com.workflo.workflo_backend.project.dtos.response.ProjectResponse;
import com.workflo.workflo_backend.project.entities.ProjectCategory;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProjectService {
    ProjectResponse createProject(CreateProject createProject) throws UserNotFoundException,
                                                                      CloudUploadException,
                                                                      UserNotVerifiedException, WorkFloException;

    default CreateProject createProjectDTO(MultipartFile image,
                                           Long id,
                                           String projectName,
                                           String summary,
                                           String description,
                                           ProjectCategory category,
                                           List<String> skills) {



        CreateProject project = new CreateProject();
        project.setCategory(category);
        project.setDescription(description);
        project.setName(projectName);
        project.setSummary(summary);
        project.setImage(image);
        project.setNeededSkills(skills);
        project.setUserCreatorId(id);
        return project;
    }
    default CreateProject createProjectDTO(Long id,
                                           String projectName,
                                           String summary,
                                           String description,
                                           ProjectCategory category,
                                           List<String> skills) {

        CreateProject project = new CreateProject();
        project.setCategory(category);
        project.setDescription(description);
        project.setName(projectName);
        project.setSummary(summary);
        project.setNeededSkills(skills);
        project.setUserCreatorId(id);
        return project;
    }

    String deleteProject(Long userId, Long projectId) throws UserNotFoundException, ProjectNotExistException, ProjectAndUserNotMatchException, WorkFloException;
}
