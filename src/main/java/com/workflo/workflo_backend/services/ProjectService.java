package com.workflo.workflo_backend.services;

import com.workflo.workflo_backend.request.CreateProjectRequest;
import com.workflo.workflo_backend.response.CreateProjectResponse;

public interface ProjectService {
    CreateProjectResponse createProject(CreateProjectRequest createProjectRequest);
}
