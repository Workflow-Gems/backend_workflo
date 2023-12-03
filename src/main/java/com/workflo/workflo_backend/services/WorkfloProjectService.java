package com.workflo.workflo_backend.services;

import com.workflo.workflo_backend.models.Project;
import com.workflo.workflo_backend.repositories.ProjectRepository;
import com.workflo.workflo_backend.request.CreateProjectRequest;
import com.workflo.workflo_backend.response.CreateProjectResponse;
import com.workflo.workflo_backend.user.models.User;
import com.workflo.workflo_backend.user.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WorkfloProjectService implements ProjectService{

    private final ProjectRepository projectRepository;

    private final UserService userService;

    private final ModelMapper modelMapper;

    public CreateProjectResponse createProject(CreateProjectRequest request) {

        // THIS IS where we should talk about DELE coz I see that you used a
        // different convention in your user based on AUTH... I want to create mine but at the
        // same time i feel like it would be a wasted effort, and it will conflict, since we are using yours... but I've
        // not fully grabbed yours yet... I'm so tired now, I will try to get some rest now
        // We will talk later in the day. I'm writing this

        User creator = userService.getUserById(request.getProjectCreatorId());
        Project project = modelMapper.map(request, Project.class);
        project.setProjectCreator(creator);
        Project savedProject = projectRepository.save(project);
        return buildResponse(savedProject);

    }

    private CreateProjectResponse buildResponse(Project project) {
        CreateProjectResponse response = new CreateProjectResponse();
        response.setProjectId(project.getProjectId());
        response.setProjectTitle(project.getProjectTitle());
        // other mappings
        return response;
    }
}
