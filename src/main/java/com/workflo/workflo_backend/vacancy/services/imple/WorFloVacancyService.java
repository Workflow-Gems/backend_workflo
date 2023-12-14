package com.workflo.workflo_backend.vacancy.services.imple;

import com.workflo.workflo_backend.exceptions.ProjectOwnerException;
import com.workflo.workflo_backend.exceptions.VacancyNotCreatedException;
import com.workflo.workflo_backend.exceptions.VacancyNotFoundException;
import com.workflo.workflo_backend.exceptions.WorkFloException;
import com.workflo.workflo_backend.project.entities.Project;
import com.workflo.workflo_backend.project.service.ProjectService;
import com.workflo.workflo_backend.user.services.UserService;
import com.workflo.workflo_backend.vacancy.dtos.request.UpdateVacancyRequest;
import com.workflo.workflo_backend.vacancy.dtos.request.VacancyRequest;
import com.workflo.workflo_backend.vacancy.dtos.response.UpdateVacancyResponse;
import com.workflo.workflo_backend.vacancy.dtos.response.VacancyResponse;
import com.workflo.workflo_backend.vacancy.entity.Vacancy;
import com.workflo.workflo_backend.vacancy.entity.VacancyStatus;
import com.workflo.workflo_backend.vacancy.repository.VacancyRepository;
import com.workflo.workflo_backend.vacancy.services.VacancyService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static com.workflo.workflo_backend.vacancy.entity.VacancyStatus.OPEN;


@Service
@AllArgsConstructor
public class WorFloVacancyService implements VacancyService {
    private final VacancyRepository repository;
    private final ProjectService projectService;
    private final UserService userService;
    private ModelMapper mapper;
    @Override
    public VacancyResponse createVacancy(VacancyRequest request) throws WorkFloException {
        Project project = projectService.findProjectById(request.getProjectIdentifier());
        if (project.getCreatorId().getId().equals(request.getUserIdentifier())) {
            Vacancy vacancy = mapper.map(request, Vacancy.class);
            vacancy.setStatus(OPEN);
            vacancy.setProject(project);
            Vacancy savedVacancy = repository.save(vacancy);
            return generateVacancyResponse(savedVacancy);
        }
        throw new VacancyNotCreatedException("Only project owner can create project...");
    }
    @Override
    @Transactional
    public UpdateVacancyResponse updateVacancy(Long userId, Long id, UpdateVacancyRequest request)
                                                            throws VacancyNotFoundException, ProjectOwnerException {
        Vacancy vacancy = findVacancyById(id);
        mapper.getConfiguration().setSkipNullEnabled(true);
        if (vacancy.getProject().getCreatorId().getId().equals(userId)) {
            mapper.map(request, vacancy);
            Vacancy savedVacancy = repository.save(vacancy);
            return generateUpdateVacancyResponse(savedVacancy);
        }
        throw new ProjectOwnerException("only the project creator can change vacancy settings...");
    }
    private UpdateVacancyResponse generateUpdateVacancyResponse(Vacancy vacancy){
        UpdateVacancyResponse response = new UpdateVacancyResponse();
        response.setMessage("Update done successfully");
        response.setStatus(vacancy.getStatus());
        return response;
    }
    private Vacancy findVacancyById(Long id) throws VacancyNotFoundException {
        return repository.findById(id)
                .orElseThrow(()-> new VacancyNotFoundException("vacancy not found..."));
    }
    private VacancyResponse generateVacancyResponse(Vacancy vacancy){
        VacancyResponse response = new VacancyResponse();
        response.setId(vacancy.getId());
        response.setMessage("Vacancy successfully opened...");
        return response;
    }

}
