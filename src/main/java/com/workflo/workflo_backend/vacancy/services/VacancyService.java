package com.workflo.workflo_backend.vacancy.services;

import com.workflo.workflo_backend.exceptions.ProjectOwnerException;
import com.workflo.workflo_backend.exceptions.VacancyNotFoundException;
import com.workflo.workflo_backend.exceptions.WorkFloException;
import com.workflo.workflo_backend.vacancy.dtos.request.UpdateVacancyRequest;
import com.workflo.workflo_backend.vacancy.dtos.request.VacancyRequest;
import com.workflo.workflo_backend.vacancy.dtos.response.UpdateVacancyResponse;
import com.workflo.workflo_backend.vacancy.dtos.response.VacancyResponse;

public interface VacancyService {
    VacancyResponse createVacancy(VacancyRequest request) throws WorkFloException;

    UpdateVacancyResponse updateVacancy(Long userId,Long id,UpdateVacancyRequest request) throws VacancyNotFoundException, ProjectOwnerException;
}
