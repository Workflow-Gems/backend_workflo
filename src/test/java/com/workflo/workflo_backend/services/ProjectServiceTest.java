package com.workflo.workflo_backend.services;


import com.workflo.workflo_backend.exceptions.CloudUploadException;
import com.workflo.workflo_backend.exceptions.UserNotFoundException;
import com.workflo.workflo_backend.exceptions.UserNotVerifiedException;
import com.workflo.workflo_backend.project.dtos.request.CreateProject;
import com.workflo.workflo_backend.project.dtos.response.ProjectResponse;
import com.workflo.workflo_backend.project.entities.ProjectCategory;
import com.workflo.workflo_backend.project.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.workflo.workflo_backend.project.entities.ProjectCategory.FINANCE;
import static com.workflo.workflo_backend.services.app.CloudServiceTest.createMultipart;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ProjectServiceTest {
    @Autowired
    private ProjectService projectService;
    @Test
    public void createProjectTest() throws CloudUploadException, UserNotFoundException, UserNotVerifiedException {
        CreateProject createProject = new CreateProject();
        createProject.setUserCreatorId(1L);
        createProject.setName("ProjectName");
        createProject.setCategory(FINANCE);
        createProject.setSummary("project Summary");
        createProject.setDescription("Project Description");
        createProject.setNeededSkills(List.of("Skills, skill1"));
        createProject.setImage(createMultipart());

        ProjectResponse response = projectService.createProject(createProject);
        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();
    }
    @Test
    public void userThatDoesNotExistCannotCreate(){
        CreateProject createProject = new CreateProject();
        createProject.setUserCreatorId(2L);
        createProject.setName("ProjectName");
        createProject.setCategory(FINANCE);
        createProject.setSummary("project Summary");
        createProject.setDescription("Project Description");
        createProject.setNeededSkills(List.of("Skills, skill1"));

        assertThrows(UserNotFoundException.class, ()-> projectService.createProject(createProject));
    }
    @Test
    public void userCanCreateMultipleProjects() throws UserNotFoundException, CloudUploadException, UserNotVerifiedException {
        CreateProject createProject = new CreateProject();
        createProject.setUserCreatorId(2L);
        createProject.setName("111AnotherProject121");
        createProject.setCategory(ProjectCategory.ART);
        createProject.setSummary("Yet Summary");
        createProject.setDescription("Another Project");
        createProject.setNeededSkills(List.of("Skill", "scaling", "..."));

        ProjectResponse response = projectService.createProject(createProject);
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(2);

    }
    @Test
    public void userCanCreateMultipleProjectsA() throws UserNotFoundException, CloudUploadException, UserNotVerifiedException {
        CreateProject createProject = new CreateProject();
        createProject.setUserCreatorId(2L);
        createProject.setName("lllll");
        createProject.setCategory(FINANCE);
        createProject.setSummary("Yesss");
        createProject.setDescription("Project");
        createProject.setNeededSkills(List.of("Skill", "scaling", "..."));
        ProjectResponse response = projectService.createProject(createProject);
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(4);
    }
    @Test
    public void userCanCreateMultipleProjectsBA() throws UserNotFoundException, CloudUploadException, UserNotVerifiedException {
        CreateProject createProject = new CreateProject();
        createProject.setUserCreatorId(1L);
        createProject.setName("name()");
        createProject.setCategory(ProjectCategory.SCIENCE);
        createProject.setSummary("summary...");
        createProject.setDescription("Project");
        createProject.setNeededSkills(List.of("Skill", "scaling", "..."));

        ProjectResponse response = projectService.createProject(createProject);
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(5);
    }
    @Test
    public void userThatIsNotEnabledCannotCreateProject(){
        CreateProject createProject = new CreateProject();
        createProject.setUserCreatorId(1L);
        createProject.setName("name()");
        createProject.setCategory(ProjectCategory.SCIENCE);
        createProject.setSummary("summary...");
        createProject.setDescription("Project");
        createProject.setNeededSkills(List.of("Skill", "scaling", "..."));

        assertThrows(UserNotVerifiedException.class, ()-> projectService.createProject(createProject));
    }
}
