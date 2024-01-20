package com.workflo.workflo_backend.trial;


import com.workflo.workflo_backend.exceptions.ProjectOwnerException;
import com.workflo.workflo_backend.exceptions.VacancyNotFoundException;
import com.workflo.workflo_backend.join_project.dtos.request.ViewVacancyRequest;
import com.workflo.workflo_backend.join_project.dtos.response.JoinProjectProjection;
import com.workflo.workflo_backend.join_project.services.JoinRequestService;
import com.workflo.workflo_backend.vacancy.entity.Vacancy;
import com.workflo.workflo_backend.vacancy.services.VacancyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.workflo.workflo_backend.exceptionHandler.errorMessages.ProjectException.PROJECT_OWNER_EXCEPTION;

@Service
@AllArgsConstructor
public class WorkFloRequestVacancy implements RequestVacancy{

    private final JoinRequestService requestService;
    private final VacancyService vacancyService;
    @Override
    public List<JoinProjectProjection> getVacancyRequests(ViewVacancyRequest request) throws VacancyNotFoundException, ProjectOwnerException {
        Vacancy vacancy = vacancyService.findVacancyById(request.getVacancyId());
        if (confirmVacancyOwner(vacancy, request.getUserId())) {
            return requestService.getVacancyRequests(request.getVacancyId(), request.getStatus());
        }
        throw new ProjectOwnerException(PROJECT_OWNER_EXCEPTION);
    }
    private boolean confirmVacancyOwner(Vacancy vacancy, Long id){
        return vacancy.getProject().getCreatorId().getId().equals(id);
    }
}
