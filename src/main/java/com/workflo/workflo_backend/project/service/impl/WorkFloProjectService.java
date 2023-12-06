package com.workflo.workflo_backend.project.service.impl;

import com.workflo.workflo_backend.appConfig.services.CloudService;
import com.workflo.workflo_backend.exceptions.ProjectAndUserNotMatchException;
import com.workflo.workflo_backend.exceptions.ProjectNotExistException;
import com.workflo.workflo_backend.exceptions.UserNotVerifiedException;
import com.workflo.workflo_backend.exceptions.WorkFloException;
import com.workflo.workflo_backend.project.dtos.request.CreateProject;
import com.workflo.workflo_backend.project.dtos.response.ProjectResponse;
import com.workflo.workflo_backend.project.entities.Project;
import com.workflo.workflo_backend.project.repository.ProjectRepository;
import com.workflo.workflo_backend.project.service.ProjectService;
import com.workflo.workflo_backend.user.models.User;
import com.workflo.workflo_backend.user.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.workflo.workflo_backend.project.entities.ProjectStatus.ONGOING;
import static com.workflo.workflo_backend.user.models.UserStatus.ACTIVE;


@Service
@AllArgsConstructor
public class WorkFloProjectService implements ProjectService {

    private final ProjectRepository repository;
    private final UserService userService;
    private final CloudService cloudService;
    @Override
    public ProjectResponse createProject(CreateProject createProject) throws WorkFloException {
        User user = userService.getUserById(createProject.getUserCreatorId());
        if (user.isEnabled() && user.getUserStatus() == ACTIVE) {
            Project project = new Project();
            if (createProject.getImage() != null) project.setImage(cloudService.upload(createProject.getImage()));
            Project savedProject = generateProject(createProject, project, user);
            return new ProjectResponse(savedProject.getId());
        }
        throw new UserNotVerifiedException(String
                .format("dear %s, kindly fill update your profile...", user.getFirstName()));
    }

    @Override
    public String deleteProject(Long userId, Long projectId) throws WorkFloException {
        User user = userService.getUserById(userId);
        Project project = findProjectById(projectId);
        if (project.getCreatorId().equals(user)){
            repository.delete(project);
            return "Successfully deleted...";
        }
        throw new ProjectAndUserNotMatchException("error, it happens that you did not create this particular project...");
    }

    public Project findProjectById(Long id) throws WorkFloException {
        return repository.findById(id)
                .orElseThrow(()-> new ProjectNotExistException("Project with this id does not exist..."));
    }

    private Project generateProject(CreateProject createProject, Project project, User user) {
        project.setDescription(createProject.getDescription());
        project.setName(createProject.getName());
        project.setSummary(createProject.getSummary());
        project.setNeededSkills(createProject.getNeededSkills());
        project.setCategory(createProject.getCategory());
        project.setCreatorId(user);
        project.setProjectStatus(ONGOING);
        project.setCreatedTime(LocalTime.now());
        project.setDateCreated(LocalDate.now());
        return repository.save(project);
    }
}
