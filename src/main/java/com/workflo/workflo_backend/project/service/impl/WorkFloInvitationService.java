package com.workflo.workflo_backend.project.service.impl;

import com.workflo.workflo_backend.appConfig.dtos.request.MailRequest;
import com.workflo.workflo_backend.appConfig.dtos.request.To;
import com.workflo.workflo_backend.appConfig.services.MailService;
import com.workflo.workflo_backend.exceptions.WorkFloException;
import com.workflo.workflo_backend.project.entities.Invitation;
import com.workflo.workflo_backend.project.entities.Project;
import com.workflo.workflo_backend.project.repository.InvitationRepository;
import com.workflo.workflo_backend.project.service.InvitationService;
import com.workflo.workflo_backend.project.service.ProjectService;
import com.workflo.workflo_backend.user.models.User;
import com.workflo.workflo_backend.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkFloInvitationService implements InvitationService {

    private final UserService userService;
    private final ProjectService projectService;
    private final InvitationRepository invitationRepository;
    private final MailService mailService;
    private final Context context;


    @Override
    public String sendInvitation(Long contributorUserId, Long projectId, String message, Long creatorId) {
        try{
            User foundContributor = userService.getUserWithId(contributorUserId);
            String email = foundContributor.getEmail();
            Project foundProject = projectService.findProjectById(projectId);
            Long projectOwnerId = foundProject.getCreatorId().getId();
            if(projectOwnerId.equals(creatorId) && !foundProject.getMembers().contains(foundContributor)){

                Invitation inviteSent = new Invitation();
                inviteSent.setMessage(message);
                inviteSent.setProjectLink("localhost:8080/api/v1/user/project?id="+ projectId);
                invitationRepository.save(inviteSent);

                MailRequest mailRequest = new MailRequest();
                mailRequest.setTo(List.of(new To(foundContributor.getFirstName(),email)));
                mailRequest.setSubject("Invitation to join project");
                mailRequest.setHtmlContent(inviteSent.getMessage() + " " + inviteSent.getProjectLink());

                mailService.sendMail(mailRequest, "", context);

                foundProject.getInvitationsSent().add(inviteSent);
                return "invite sent";

            }}

        catch(WorkFloException e){
            System.err.println(e.getMessage());
        }
        return "";
    }

}
