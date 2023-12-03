package com.workflo.workflo_backend.services;

import com.workflo.workflo_backend.models.Project;
import com.workflo.workflo_backend.repositories.ProjectRepository;
import com.workflo.workflo_backend.request.CreateProjectRequest;
import com.workflo.workflo_backend.response.CreateProjectResponse;
import com.workflo.workflo_backend.user.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class WorkfloProjectServiceTest {


    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectRepository projectRepo;

    @Test
    public void testCreateProject() {
        User user = new User();
        CreateProjectRequest request = new CreateProjectRequest();
        request.setProjectTitle("Chibuzor Owonikoko Fish Farming Project");
        request.setProjectDescription("This project is aimed at building a MVP");
        request.setProjectCreator(user);
        CreateProjectResponse response = projectService.createProject(request);
        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals(request.getProjectTitle(), response.getTitle());

        Project saved = projectRepo.findById(response.getId()).get();
        assertEquals(request.getProjectDescription(), saved.getProjectDescription());
        assertEquals(user, saved.getCreator());

}