package com.workflo.workflo_backend.controllers;


import com.workflo.workflo_backend.exceptions.CloudUploadException;
import com.workflo.workflo_backend.project.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.workflo.workflo_backend.project.entities.ProjectCategory.SCIENCE;
import static com.workflo.workflo_backend.services.app.CloudServiceTest.createMultipart;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProjectControllerTest {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void createProject() throws Exception, CloudUploadException {
        mockMvc.perform(multipart(POST, "/api/v1/user/project")
                .file(new MockMultipartFile("image", createMultipart().getBytes()))
                .part(new MockPart("userId", new byte[]{49}))
                .part(new MockPart("project_name", "ProjectName".getBytes()))
                .part(new MockPart("summary", "SummaryHere".getBytes()))
                .part(new MockPart("description", "MyDescription".getBytes()))
                .part(new MockPart("category", SCIENCE.toString().getBytes()))
                .part(new MockPart("skills", List.of("").toString().getBytes()))
                .contentType(MULTIPART_FORM_DATA_VALUE))
                .andExpect(status().is2xxSuccessful()).andDo(print());
    }
    @Test
    public void createProjectWithoutImage() throws Exception, CloudUploadException {
        mockMvc.perform(post("/api/v1/user/project")
//                .file(new MockMultipartFile("image", createMultipart().getBytes()))
                .param("userId", Long.valueOf(1).toString())
                .param("project_name", "ProjectName")
                .param("summary", "SummaryHere")
                .param("description", "MyDescription")
                .param("category", SCIENCE.toString())
                .param("skills", List.of("").toString())
                .contentType(APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful()).andDo(print());
    }
    @Test
    public void deleteProject() throws Exception {
        mockMvc.perform(delete("/api/v1/user/project/1/1"))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }
    @Test
    public void getAllProjects() throws Exception {
        mockMvc.perform(get("/api/v1/user/project/1/10"))
                .andExpect(status().is2xxSuccessful()).andDo(print());
    }
}
