package com.workflo.workflo_backend.join_project.services;

import com.workflo.workflo_backend.exceptions.WorkFloException;
import com.workflo.workflo_backend.join_project.dtos.request.JoinProjectRequest;
import com.workflo.workflo_backend.join_project.dtos.response.JoinProjectResponse;

public interface JoinRequestService {
    JoinProjectResponse bidForProject(JoinProjectRequest request) throws WorkFloException;
}
