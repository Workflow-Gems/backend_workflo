package com.workflo.workflo_backend.services;


import com.workflo.workflo_backend.exceptions.ProjectNotExistException;
import com.workflo.workflo_backend.exceptions.ProjectOwnerException;
import com.workflo.workflo_backend.exceptions.UserNotVerifiedException;
import com.workflo.workflo_backend.exceptions.WorkFloException;
import com.workflo.workflo_backend.join_project.dtos.request.JoinProjectRequest;
import com.workflo.workflo_backend.join_project.dtos.response.JoinProjectResponse;
import com.workflo.workflo_backend.join_project.services.JoinRequestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static com.workflo.workflo_backend.join_project.models.JoinProjectStatus.PENDING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        request.setProjectId(1L);
        request.setMessage("Kindly, add me... i am interested in building.");
        JoinProjectResponse response = requestService.bidForProject(request);
        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(PENDING);
    }
    @Test
    public void userCannotBidForAProjectThatDoesNotExist(){
        JoinProjectRequest request = new JoinProjectRequest();
        request.setUserId(1L);
        request.setProjectId(1L);
        assertThrows(ProjectNotExistException.class, ()-> requestService.bidForProject(request));
    }
    @Test
    public void userThatIsNotEnabledCannotRequestToJoinProject(){
        JoinProjectRequest request = new JoinProjectRequest();
        request.setUserId(3L);
        request.setProjectId(4L);
        assertThrows(UserNotVerifiedException.class, ()-> requestService.bidForProject(request));
    }
    @Test
    public void projectOwnerCannotBidButRatherBeRemindedThatUserIsTheOwner() throws WorkFloException {
        JoinProjectRequest request = new JoinProjectRequest();
        request.setUserId(2L);
        request.setProjectId(4L);
        assertThrows(ProjectOwnerException.class, ()->requestService.bidForProject(request));
    }
}
