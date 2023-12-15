package com.workflo.workflo_backend.services;


import com.workflo.workflo_backend.exceptions.*;
import com.workflo.workflo_backend.join_project.dtos.request.DecideProjectRequest;
import com.workflo.workflo_backend.join_project.dtos.request.JoinProjectRequest;
import com.workflo.workflo_backend.join_project.dtos.response.DecideProjectResponse;
import com.workflo.workflo_backend.join_project.dtos.response.JoinProjectProjection;
import com.workflo.workflo_backend.join_project.dtos.response.JoinProjectResponse;
import com.workflo.workflo_backend.join_project.services.JoinRequestService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.workflo.workflo_backend.join_project.models.JoinProjectStatus.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class JoinProjectRequestTest {
    @Autowired
    private JoinRequestService requestService;
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void anotherUserCanRequestToJoinAProject() throws WorkFloException {
        JoinProjectRequest request = new JoinProjectRequest();
        request.setUserId(2L);
        request.setVacancyId(2L);
//        request.setMessage("Kindly, add me... i am interested in building.");
        JoinProjectResponse response = requestService.bidForProject(request);
        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(PENDING);
    }
    @Test
    public void userCannotBidForAProjectWhoseVacancyIsNotOpened(){
        JoinProjectRequest request = new JoinProjectRequest();
        request.setUserId(2L);
        request.setVacancyId(2L);
        assertThrows(BidRequestNotAllowedException.class, ()-> requestService.bidForProject(request));
    }
    @Test
    public void userThatIsNotEnabledCannotRequestToJoinProject(){
        JoinProjectRequest request = new JoinProjectRequest();
        request.setUserId(3L);
        request.setVacancyId(4L);
        assertThrows(UserNotVerifiedException.class, ()-> requestService.bidForProject(request));
    }
    @Test
    public void projectOwnerCannotBidButRatherBeRemindedThatUserIsTheOwner() throws WorkFloException {
        JoinProjectRequest request = new JoinProjectRequest();
        request.setUserId(1L);
        request.setVacancyId(1L);
        assertThrows(ProjectOwnerException.class, ()->requestService.bidForProject(request));
    }
    @Test
    public void showAllRequestOfAParticularVacancy(){
        List<JoinProjectProjection> response = requestService.getVacancyRequests(1L, PENDING);

        assertThat(response).isNotNull();
        assertThat(response.size()).isEqualTo(5L);
        response.iterator().forEachRemaining(res -> assertThat(res.getStatus()).isEqualTo(PENDING));
    }

    //yet to draw endpoint
    @Test
    public void userCanBeAcceptedIntoAProject() throws ProjectOwnerException, DuplicateProjectMemberException {
        DecideProjectRequest request = new DecideProjectRequest();
        request.setUserId(1L);
        request.setRequestId(1L);
        request.setStatus(ACCEPTED);

        DecideProjectResponse response = requestService.decideBidRequest(request);
        assertThat(response).isNotNull();
        assertThat(response.getProjectMembers()).isEqualTo(4L);
    }
    @Test
    public void userCanBeAcceptedIntoAProjectAB() throws ProjectOwnerException, DuplicateProjectMemberException {
        DecideProjectRequest request = new DecideProjectRequest();
        request.setUserId(1L);
        request.setRequestId(3L);
        request.setStatus(ACCEPTED);

        DecideProjectResponse response = requestService.decideBidRequest(request);
        assertThat(response).isNotNull();
        assertThat(response.getProjectMembers()).isEqualTo(6L);
    }
    @Test
    public void userCanBeRejected() throws DuplicateProjectMemberException, ProjectOwnerException {
        DecideProjectRequest request = new DecideProjectRequest();
        request.setUserId(1L);
        request.setRequestId(9L);
        request.setStatus(DECLINED);
        DecideProjectResponse response = requestService.decideBidRequest(request);
        assertThat(response).isNotNull();
        assertThat(response.getProjectMembers()).isEqualTo(6L);
        assertThat(response.getStatus()).isEqualTo(DECLINED);
    }
    @Test
    public void userCanBeAcceptedIntoAProjectA() throws ProjectOwnerException, DuplicateProjectMemberException {
        DecideProjectRequest request = new DecideProjectRequest();
        request.setUserId(1L);
        request.setRequestId(8L);
        request.setStatus(ACCEPTED);

        DecideProjectResponse response = requestService.decideBidRequest(request);
        assertThat(response).isNotNull();
        assertThat(response.getProjectMembers()).isEqualTo(1L);
    }

    @Test
    public void onlyProjectOwnerCanAcceptRequest(){
        DecideProjectRequest request = new DecideProjectRequest();
        request.setUserId(2L);
        request.setRequestId(2L);
        request.setStatus(ACCEPTED);
        assertThrows(ProjectOwnerException.class, ()->requestService.decideBidRequest(request));
    }
    @Test
    public void aParticularUserCannotBeAddedMoreThanOneTimeInAProject(){
        DecideProjectRequest request = new DecideProjectRequest();
        request.setUserId(1L);
        request.setRequestId(4L);
        request.setStatus(ACCEPTED);
        assertThrows(DuplicateProjectMemberException.class, () -> requestService.decideBidRequest(request));
    }

    @Test
    public void userCanCheckALlItRequest(){
        List<JoinProjectResponse> response = requestService.findUserRequestById(3L);
        for (JoinProjectResponse res : response){
            log.info("here :: {}", res);
        }
        assertThat(response).isNotNull();
    }
}
