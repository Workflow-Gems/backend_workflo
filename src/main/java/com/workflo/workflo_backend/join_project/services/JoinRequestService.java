package com.workflo.workflo_backend.join_project.services;

import com.workflo.workflo_backend.exceptions.DuplicateProjectMemberException;
import com.workflo.workflo_backend.exceptions.ProjectOwnerException;
import com.workflo.workflo_backend.exceptions.WorkFloException;
import com.workflo.workflo_backend.join_project.dtos.request.DecideProjectRequest;
import com.workflo.workflo_backend.join_project.dtos.request.JoinProjectRequest;
import com.workflo.workflo_backend.join_project.dtos.response.DecideProjectResponse;
import com.workflo.workflo_backend.join_project.dtos.response.JoinProjectProjection;
import com.workflo.workflo_backend.join_project.dtos.response.JoinProjectResponse;
import com.workflo.workflo_backend.join_project.models.JoinProjectStatus;

import java.util.List;

public interface JoinRequestService {
    JoinProjectResponse bidForProject(JoinProjectRequest request) throws WorkFloException;

    List<JoinProjectProjection> getVacancyRequests(Long id, JoinProjectStatus status);
    DecideProjectResponse decideBidRequest(DecideProjectRequest request) throws ProjectOwnerException, DuplicateProjectMemberException;

    List<JoinProjectResponse> findUserRequestById(long id);
}
