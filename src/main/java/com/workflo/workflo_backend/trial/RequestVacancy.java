package com.workflo.workflo_backend.trial;

import com.workflo.workflo_backend.exceptions.ProjectOwnerException;
import com.workflo.workflo_backend.exceptions.VacancyNotFoundException;
import com.workflo.workflo_backend.join_project.dtos.request.ViewVacancyRequest;
import com.workflo.workflo_backend.join_project.dtos.response.JoinProjectProjection;

import java.util.List;

public interface RequestVacancy {
    List<JoinProjectProjection> getVacancyRequests(ViewVacancyRequest request) throws VacancyNotFoundException, ProjectOwnerException;
}
