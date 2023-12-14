package com.workflo.workflo_backend.services;


import com.workflo.workflo_backend.exceptions.ProjectOwnerException;
import com.workflo.workflo_backend.exceptions.VacancyNotCreatedException;
import com.workflo.workflo_backend.exceptions.VacancyNotFoundException;
import com.workflo.workflo_backend.exceptions.WorkFloException;
import com.workflo.workflo_backend.vacancy.dtos.request.UpdateVacancyRequest;
import com.workflo.workflo_backend.vacancy.dtos.request.VacancyRequest;
import com.workflo.workflo_backend.vacancy.dtos.response.UpdateVacancyResponse;
import com.workflo.workflo_backend.vacancy.dtos.response.VacancyResponse;
import com.workflo.workflo_backend.vacancy.services.VacancyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.workflo.workflo_backend.vacancy.entity.VacancyStatus.CLOSED;
import static com.workflo.workflo_backend.vacancy.entity.VacancyStatus.OPEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class VacancyServiceTest {
    @Autowired
    private VacancyService vacancyService;
    @Test
    public void projectOwnerCanCreateVacancy() throws WorkFloException {
        VacancyRequest request = new VacancyRequest();
        request.setProjectIdentifier(1L);
        request.setUserIdentifier(1L);
        request.setNeededSkills(List.of("frontend", "backend"));
        request.setText("We are open for collaboration");
        VacancyResponse response = vacancyService.createVacancy(request);
        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();
    }
    @Test
    public void aUserWhoIsNotOwnerOfAProjectCannotOpenVacancyForProjectTest(){
        VacancyRequest request = new VacancyRequest();
        request.setProjectIdentifier(1L);
        request.setUserIdentifier(2L);
        request.setNeededSkills(List.of("frontend", "backend"));
        request.setText("We are open for collaboration");
        assertThrows(VacancyNotCreatedException.class,()->vacancyService.createVacancy(request));
    }
    @Test
    public void updateVacancyTest() throws VacancyNotFoundException, ProjectOwnerException {
        UpdateVacancyRequest request = new UpdateVacancyRequest();
        Long userId = 1L;
        Long id = 2L;
        request.setNeededSkills(List.of("DevOps"));
        request.setStatus(OPEN);

        UpdateVacancyResponse response = vacancyService.updateVacancy(userId, id,request);

        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isNotNull();
        assertThat(response.getStatus()).isEqualTo(OPEN);
    }
    @Test
    public void aUserWhoIsNotProjectOwnerCannotUpdateTheParticularProjectVacancy(){
        Long userId = 2L;
        Long id = 2L;
        UpdateVacancyRequest request = new UpdateVacancyRequest();
        request.setStatus(CLOSED);
        assertThrows(ProjectOwnerException.class, ()->vacancyService.updateVacancy(userId, id,request));
    }
    @Test
    public void getVacancyByIdThatDoesNotExistThrowsAnException(){
        UpdateVacancyRequest request = new UpdateVacancyRequest();
        Long userId = 1L;
        Long id = 3L;
        request.setNeededSkills(List.of("DevOps"));
        request.setStatus(CLOSED);

        assertThrows(VacancyNotFoundException.class, ()-> vacancyService.updateVacancy(userId, id, request));
    }
}
