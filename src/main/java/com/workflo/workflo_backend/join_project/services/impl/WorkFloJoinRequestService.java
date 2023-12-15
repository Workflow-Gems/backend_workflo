package com.workflo.workflo_backend.join_project.services.impl;

import com.workflo.workflo_backend.appConfig.dtos.request.MailRequest;
import com.workflo.workflo_backend.appConfig.dtos.request.To;
import com.workflo.workflo_backend.appConfig.services.MailService;
import com.workflo.workflo_backend.exceptions.*;
import com.workflo.workflo_backend.join_project.dtos.request.DecideProjectRequest;
import com.workflo.workflo_backend.join_project.dtos.request.JoinProjectRequest;
import com.workflo.workflo_backend.join_project.dtos.response.DecideProjectResponse;
import com.workflo.workflo_backend.join_project.dtos.response.JoinProjectProjection;
import com.workflo.workflo_backend.join_project.dtos.response.JoinProjectResponse;
import com.workflo.workflo_backend.join_project.models.JoinProject;
import com.workflo.workflo_backend.join_project.models.JoinProjectStatus;
import com.workflo.workflo_backend.join_project.repository.JoinProjectRepository;
import com.workflo.workflo_backend.join_project.services.JoinRequestService;
import com.workflo.workflo_backend.project.entities.Project;
import com.workflo.workflo_backend.project.service.ProjectService;
import com.workflo.workflo_backend.user.dtos.response.FoundUserResponse;
import com.workflo.workflo_backend.user.models.User;
import com.workflo.workflo_backend.user.services.UserService;
import com.workflo.workflo_backend.vacancy.entity.Vacancy;
import com.workflo.workflo_backend.vacancy.services.VacancyService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

import static com.workflo.workflo_backend.join_project.models.JoinProjectStatus.ACCEPTED;
import static com.workflo.workflo_backend.join_project.models.JoinProjectStatus.PENDING;
import static com.workflo.workflo_backend.vacancy.entity.VacancyStatus.OPEN;


@AllArgsConstructor
@Service
@Slf4j
public class WorkFloJoinRequestService implements JoinRequestService {
    private final JoinProjectRepository repository;
    private final UserService userService;
    private final VacancyService vacancyService;
    private final ProjectService projectService;
    private final Context context;
    private final ModelMapper mapper;
    private final MailService mailService;

    @Override
    @Transactional
    public JoinProjectResponse bidForProject(JoinProjectRequest request) throws WorkFloException {
        User user = userService.getUserWithId(request.getUserId());
        Vacancy vacancy = vacancyService.findVacancyById(request.getVacancyId());
        if (generateOrOrNot(user, vacancy)){
            JoinProject joinProject = createJoinRequest(request, vacancy, user);
            JoinProject savedRequest = repository.save(joinProject);
            setContext(vacancy, request, user);
            return generateJoinRequestResponse(savedRequest);
        }
        return throwError(user, vacancy);
    }
    @Override
    @Transactional
    public List<JoinProjectProjection> getVacancyRequests(Long id, JoinProjectStatus status) {
        List<JoinProject> joinProjects = repository.findByVacancyIdAndStatus(id, status);
        List<JoinProjectProjection> result = new ArrayList<>();

        joinProjects.stream().iterator().forEachRemaining(res -> result.add(generateJoinProjectProjection(res)));

//        for (JoinProject project : joinProjects) result.add(generateJoinProjectProjection(project));

        return result;
    }
    @Override
    @Transactional
    public DecideProjectResponse decideBidRequest(DecideProjectRequest request) throws ProjectOwnerException,
                                                                                DuplicateProjectMemberException {
        JoinProject joinProject = repository.findById(request.getRequestId())
                                            .orElseThrow();
        Vacancy vacancy = joinProject.getVacancy();
        User requestee = joinProject.getUser();
        Project project = vacancy.getProject();
        User user = project.getCreatorId();
        if (check(user, request, requestee, project)){
            if (request.getStatus().equals(ACCEPTED)) {
                project.getMembers().add(requestee);
                projectService.updateProject(project);
            }
            joinProject.setStatus(request.getStatus());
            JoinProject updatedJoinedProject = repository.save(joinProject);
            return generateJoinProjectResponse(updatedJoinedProject, project);
        }
        return throwError(user, request);
    }

    @Override
    @Transactional
    public List<JoinProjectResponse> findUserRequestById(long id) {
        List<JoinProject> projects = repository.findUserId(id);
        List<JoinProjectResponse> response = new ArrayList<>();
        for (JoinProject project : projects){
            response.add(mapper.map(project, JoinProjectResponse.class));
        }
        return response;
    }

    private DecideProjectResponse throwError(User user, DecideProjectRequest request) throws ProjectOwnerException, DuplicateProjectMemberException {
        if (!user.getId().equals(request.getUserId()))
            throw new ProjectOwnerException("Only project owner allowed to perform this action");
        else throw new DuplicateProjectMemberException("User already added to this project...");
    }
    private boolean check(User user, DecideProjectRequest request, User requestee, Project project){
        return user.getId().equals(request.getUserId())
                           && !project.getMembers().contains(requestee);
    }
    private DecideProjectResponse generateJoinProjectResponse(JoinProject joinProject, Project project){
        DecideProjectResponse response = new DecideProjectResponse();
        response.setStatus(joinProject.getStatus());
        response.setProjectMembers((long)project.getMembers().size());
        return response;
    }
    private JoinProjectProjection generateJoinProjectProjection(JoinProject project){
        User user = project.getUser();
        FoundUserResponse mappedUser = mapper.map(user, FoundUserResponse.class);
        JoinProjectProjection projection = mapper.map(project, JoinProjectProjection.class);
        projection.setRequester(mappedUser);
        return projection;
    }
    private JoinProjectResponse throwError(User user, Vacancy vacancy) throws WorkFloException {
        if (vacancy.getProject().getCreatorId().equals(user))
            throw new ProjectOwnerException(
                    String.format("dear %s, you cannot request to join your own project", user.getFirstName()));
        else if (!user.isEnabled())
            throw new UserNotVerifiedException(String.format("dear %s, kindly complete your to continue", user.getFirstName()));
        else throw new BidRequestNotAllowedException("Project is currently not accepting any bid...");
    }
    private boolean generateOrOrNot(User user, Vacancy vacancy){
        return user.isEnabled() &&
               vacancy.getStatus() == OPEN &&
               !vacancy.getProject().getCreatorId().equals(user);
    }
    private JoinProject createJoinRequest(JoinProjectRequest request, Vacancy vacancy, User user){
        return JoinProject.builder()
                .message(request.getMessage())
                .status(PENDING)
                .vacancy(vacancy)
                .user(user)
                .build();
    }
    private JoinProjectResponse generateJoinRequestResponse(JoinProject request){
        return JoinProjectResponse.builder()
                .id(request.getId()).status(request.getStatus())
                .message("Kindly wait for the project owner to review")
                .build();
    }
    private void sendMail(MailRequest request) throws SendMailException {
        mailService.sendMail(request, "request_for_project", context);
    }
    private void setContext(Vacancy vacancy, JoinProjectRequest request, User user) throws SendMailException {
        if (request.getMessage() != null) context.setVariable("body", request.getMessage());
        else context.setVariable("body", defaultRequestMessage(vacancy.getProject().getCreatorId().getFirstName(),
                                                                     user.getFirstName(), vacancy.getProject().getName()));
        mailRequest(vacancy);
    }
    private void mailRequest(Vacancy vacancy) throws SendMailException {
        MailRequest request = new MailRequest();
        To to = new To(vacancy.getProject().getCreatorId().getFirstName(), vacancy.getProject().getCreatorId().getEmail());
        request.setTo(List.of(to));
        request.setSubject("Request to Contribute to Project...");
        sendMail(request);
    }
    private String defaultRequestMessage(String creatorName, String requesterName, String projectName){
        return String
                .format("""
                        Dear %s, \s
                        %s just requested to contribute to your project "%s".
                        kindly review their application...
                        
                        WorkFlo...""", creatorName, requesterName, projectName);
    }
}
