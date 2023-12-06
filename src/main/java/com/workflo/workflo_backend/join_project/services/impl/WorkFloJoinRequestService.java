package com.workflo.workflo_backend.join_project.services.impl;

import com.workflo.workflo_backend.appConfig.dtos.request.MailRequest;
import com.workflo.workflo_backend.appConfig.dtos.request.To;
import com.workflo.workflo_backend.appConfig.services.MailService;
import com.workflo.workflo_backend.exceptions.ProjectOwnerException;
import com.workflo.workflo_backend.exceptions.SendMailException;
import com.workflo.workflo_backend.exceptions.UserNotVerifiedException;
import com.workflo.workflo_backend.exceptions.WorkFloException;
import com.workflo.workflo_backend.join_project.dtos.request.JoinProjectRequest;
import com.workflo.workflo_backend.join_project.dtos.response.JoinProjectResponse;
import com.workflo.workflo_backend.join_project.models.JoinProject;
import com.workflo.workflo_backend.join_project.repository.JoinProjectRepository;
import com.workflo.workflo_backend.join_project.services.JoinRequestService;
import com.workflo.workflo_backend.project.entities.Project;
import com.workflo.workflo_backend.project.service.ProjectService;
import com.workflo.workflo_backend.user.models.User;
import com.workflo.workflo_backend.user.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.List;

import static com.workflo.workflo_backend.join_project.models.JoinProjectStatus.PENDING;


@AllArgsConstructor
@Service
public class WorkFloJoinRequestService implements JoinRequestService {
    private final JoinProjectRepository repository;
    private final UserService userService;
    private final ProjectService projectService;
    private final Context context;
    private final MailService mailService;

    @Override
    public JoinProjectResponse bidForProject(JoinProjectRequest request) throws WorkFloException {
        User user = userService.getUserById(request.getUserId());
        if (user.isEnabled()) {
            Project project = projectService.findProjectById(request.getProjectId());
            if (!project.getCreatorId().equals(user)) {
                JoinProject joinProject = createJoinRequest(request, project, user);
                JoinProject savedRequest = repository.save(joinProject);
                setContext(project,request,user);
                return generateResponse(savedRequest);
            }
            throw new ProjectOwnerException(
                    String.format("dear %s, you cannot request to join your own project", user.getFirstName()));
        }
        throw new UserNotVerifiedException(String.format("dear %s, kindly complete your to continue", user.getFirstName()));
    }
    private JoinProject createJoinRequest(JoinProjectRequest request, Project project, User user){
        return JoinProject.builder()
                .message(request.getMessage())
                .status(PENDING).project(project)
                .user(user).build();
    }
    private JoinProjectResponse generateResponse(JoinProject request){
        return JoinProjectResponse.builder()
                .id(request.getId()).status(request.getStatus())
                .message("Kindly wait for the project owner to review")
                .build();
    }
    private void sendMail(MailRequest request) throws SendMailException {
        mailService.sendMail(request, "request_for_project", context);
    }
    private void setContext(Project project, JoinProjectRequest request, User user) throws SendMailException {
        if (request.getMessage() != null) context.setVariable("body", request.getMessage());
        else context.setVariable("body", defaultRequestMessage(project.getCreatorId().getFirstName(),
                                                                     user.getFirstName(), project.getName()));
        mailRequest(project);
    }
    private void mailRequest(Project project) throws SendMailException {
        MailRequest request = new MailRequest();
        To to = new To(project.getCreatorId().getFirstName(), project.getCreatorId().getEmail());
        request.setTo(List.of(to));
        request.setSubject("Request to Contribute to Project...");
        sendMail(request);
    }
    private String defaultRequestMessage(String creatorName, String requesterName, String projectName){
        return String
                .format("Dear %s, %s just requested to contribute to your project %s." +
                        " kindly review their application... WorkFlo...", creatorName, requesterName, projectName);
    }
}
