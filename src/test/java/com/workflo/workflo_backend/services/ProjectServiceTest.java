package com.workflo.workflo_backend.services;


import com.workflo.workflo_backend.exceptions.*;
import com.workflo.workflo_backend.project.dtos.request.CreateProjectRequest;
import com.workflo.workflo_backend.project.dtos.response.ProjectProjection;
import com.workflo.workflo_backend.project.dtos.response.ProjectResponse;
import com.workflo.workflo_backend.project.entities.ProjectCategory;
import com.workflo.workflo_backend.project.service.ProjectService;
import com.workflo.workflo_backend.vacancy.dtos.request.VacancyRequest;
import com.workflo.workflo_backend.vacancy.dtos.response.VacancyResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.List;

import static com.workflo.workflo_backend.project.entities.ProjectCategory.*;
import static com.workflo.workflo_backend.services.app.CloudServiceTest.createMultipart;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Slf4j
public class ProjectServiceTest {
    @Autowired
    private ProjectService projectService;
    @Test
    public void createProjectTest() throws WorkFloException {
        CreateProjectRequest createProjectRequest = new CreateProjectRequest();
        createProjectRequest.setUserCreatorId(1L);
        createProjectRequest.setName("ProjectName");
        createProjectRequest.setCategory(AGRICULTURE);
        createProjectRequest.setSummary("project Summary");
        createProjectRequest.setDescription("Project Description");
        createProjectRequest.setNeededSkills(List.of("Skills, skill1"));
        createProjectRequest.setImage(createMultipart());

        ProjectResponse response = projectService.createProject(createProjectRequest);
        assertThat(response).isNotNull();
        assertThat(response.getIdentifier()).isNotNull();
    }
    @Test
    public void userThatDoesNotExistCannotCreate(){
        CreateProjectRequest createProjectRequest = new CreateProjectRequest();
        createProjectRequest.setUserCreatorId(2L);
        createProjectRequest.setName("ProjectName");
        createProjectRequest.setCategory(FINANCE);
        createProjectRequest.setSummary("project Summary");
        createProjectRequest.setDescription("Project Description");
        createProjectRequest.setNeededSkills(List.of("Skills, skill1"));

        assertThrows(UserNotFoundException.class, ()-> projectService.createProject(createProjectRequest));
    }
    @Test
    public void userCanCreateMultipleProjects() throws WorkFloException {
        CreateProjectRequest createProjectRequest = new CreateProjectRequest();
        createProjectRequest.setUserCreatorId(1L);
        createProjectRequest.setName("111AnotherProject121");
        createProjectRequest.setCategory(ART);
        createProjectRequest.setSummary("Yet Summary");
        createProjectRequest.setDescription("Another Project");
        createProjectRequest.setNeededSkills(List.of("Skill", "scaling", "..."));

        ProjectResponse response = projectService.createProject(createProjectRequest);
        assertThat(response).isNotNull();
        assertThat(response.getIdentifier()).isEqualTo(2);

    }
    @Test
    public void userCanCreateMultipleProjectsA() throws WorkFloException {
        CreateProjectRequest createProjectRequest = new CreateProjectRequest();
        createProjectRequest.setUserCreatorId(1L);
        createProjectRequest.setName("lllll");
        createProjectRequest.setCategory(FINANCE);
        createProjectRequest.setSummary("Yesss");
        createProjectRequest.setDescription("Project");
        createProjectRequest.setNeededSkills(List.of("Skill", "scaling", "..."));
        ProjectResponse response = projectService.createProject(createProjectRequest);
        assertThat(response).isNotNull();
        assertThat(response.getIdentifier()).isEqualTo(4);
    }
    @Test
    public void userCanCreateMultipleProjectsBA() throws WorkFloException {
        CreateProjectRequest createProjectRequest = new CreateProjectRequest();
        createProjectRequest.setUserCreatorId(1L);
        createProjectRequest.setName("name()");
        createProjectRequest.setCategory(ProjectCategory.SCIENCE);
        createProjectRequest.setSummary("summary...");
        createProjectRequest.setDescription("Project");
        createProjectRequest.setNeededSkills(List.of("Skill", "scaling", "..."));

        ProjectResponse response = projectService.createProject(createProjectRequest);
        assertThat(response).isNotNull();
        assertThat(response.getIdentifier()).isEqualTo(5);
    }
    @Test
    public void userThatIsNotEnabledCannotCreateProject(){
        CreateProjectRequest createProjectRequest = new CreateProjectRequest();
        createProjectRequest.setUserCreatorId(1L);
        createProjectRequest.setName("name()");
        createProjectRequest.setCategory(ProjectCategory.SCIENCE);
        createProjectRequest.setSummary("summary...");
        createProjectRequest.setDescription("Project");
        createProjectRequest.setNeededSkills(List.of("Skill", "scaling", "..."));

        assertThrows(UserNotVerifiedException.class, ()-> projectService.createProject(createProjectRequest));
    }
    @Test
    public void ownerOfAProjectCanDeleteAProject() throws WorkFloException {
        Long userId = 1L;
        Long projectId = 2L;
        String response = projectService.deleteProject(userId, projectId);
        assertThat(response).isNotNull();
    }
    @Test
    public void ownerCannotDeleteProjectThatDoesNotExist(){
        Long userId = 1L;
        Long projectId = 1L;
        assertThrows(ProjectNotExistException.class, ()-> projectService.deleteProject(userId, projectId));
    }
    @Test
    public void aUserWhoIsNotOwnerCannotDeletedProject(){
        Long userId = 2L;
        Long projectId = 3L;
        assertThrows(ProjectAndUserNotMatchException.class, ()-> projectService.deleteProject(userId, projectId));
    }
    @Test
    public void getAllProject(){
        Page<ProjectResponse> projects = projectService.getAllProjects(1, 3);
        assertThat(projects).isNotNull();
        assertThat(projects.getTotalPages()).isEqualTo(1);
    }
    @Test
    public void viewProject() throws WorkFloException {
        ProjectProjection project = projectService.viewProjectById(1L);
        assertThat(project).isNotNull();
        assertThat(project.getCategory()).isEqualTo(AGRICULTURE);
    }
    @Test
    public void viewProjectsCreatedBySelfTest() throws ProjectNotExistException {
        List<ProjectResponse> responses = projectService.viewCreatedProjectsByUser(1L);
        assertThat(responses).isNotNull();
        assertThat(responses.size()).isEqualTo(1);
    }
}
