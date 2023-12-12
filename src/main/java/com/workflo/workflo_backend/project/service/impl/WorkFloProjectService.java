package com.workflo.workflo_backend.project.service.impl;

import com.workflo.workflo_backend.appConfig.services.CloudService;
import com.workflo.workflo_backend.exceptions.*;
import com.workflo.workflo_backend.project.dtos.request.CreateProjectRequest;
import com.workflo.workflo_backend.project.dtos.response.ProjectResponse;
import com.workflo.workflo_backend.project.entities.Project;
import com.workflo.workflo_backend.project.repository.ProjectRepository;
import com.workflo.workflo_backend.project.service.ProjectService;
import com.workflo.workflo_backend.user.models.User;
import com.workflo.workflo_backend.user.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.workflo.workflo_backend.project.entities.ProjectStatus.ONGOING;
import static com.workflo.workflo_backend.user.models.UserStatus.ACTIVE;


@Service
@AllArgsConstructor
public class WorkFloProjectService implements ProjectService {

    private final ProjectRepository repository;
    private final UserService userService;
    private final CloudService cloudService;
    private final ModelMapper mapper;
    @Override
    public ProjectResponse createProject(CreateProjectRequest createProjectRequest) throws WorkFloException {
        User user = userService.getUserWithId(createProjectRequest.getUserCreatorId());
        if (user.isEnabled() && user.getUserStatus() == ACTIVE) {
            Project project = generateProject(createProjectRequest, user);
            ProjectResponse response = mapper.map(project, ProjectResponse.class);
            response.setId(project.getIdentifier());
            return response;
        }
        throw new UserNotVerifiedException(String
                .format("dear %s, kindly fill update your profile...", user.getFirstName()));
    }
    @Override
    public String deleteProject(Long userId, Long projectId) throws WorkFloException {
        Project project = findProjectById(projectId);
        if (project.getCreatorId().getId().equals(userId)){
            repository.delete(project);
            return "Successfully deleted...";
        }
        throw new ProjectAndUserNotMatchException("error, it happens that you did not create this particular project...");
    }

    public Project findProjectById(Long id) throws WorkFloException {
        return repository.findById(id)
                .orElseThrow(()-> new ProjectNotExistException("Project with this id does not exist..."));
    }

    @Override
    public Page<ProjectResponse> getAllProjects(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Project> projectsPage = repository.findAll(pageable);
        return projectsPage.map(project -> mapper.map(project, ProjectResponse.class));
    }
    private Project generateProject(CreateProjectRequest createProjectRequest, User user) throws CloudUploadException {
        Project project = mapper.map(createProjectRequest, Project.class);
        if (createProjectRequest.getImage() != null) project.setImage(cloudService.upload(createProjectRequest.getImage()));
        project.setCreatorId(user);
        project.setProjectStatus(ONGOING);
        return repository.save(project);
    }
}
