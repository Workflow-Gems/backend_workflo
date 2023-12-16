package com.workflo.workflo_backend.vacancy.controller;


import com.workflo.workflo_backend.exceptions.ProjectOwnerException;
import com.workflo.workflo_backend.exceptions.VacancyNotFoundException;
import com.workflo.workflo_backend.exceptions.WorkFloException;
import com.workflo.workflo_backend.join_project.dtos.request.ViewVacancyRequest;
import com.workflo.workflo_backend.join_project.dtos.response.JoinProjectProjection;
import com.workflo.workflo_backend.trial.RequestVacancy;
import com.workflo.workflo_backend.vacancy.dtos.request.UpdateVacancyRequest;
import com.workflo.workflo_backend.vacancy.dtos.request.VacancyRequest;
import com.workflo.workflo_backend.vacancy.dtos.response.UpdateVacancyResponse;
import com.workflo.workflo_backend.vacancy.dtos.response.VacancyResponse;
import com.workflo.workflo_backend.vacancy.services.VacancyService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/user/project/vacancy")
@AllArgsConstructor
public class VacancyController {

    private final VacancyService vacancyService;
    private final RequestVacancy requestVacancy;

    @PostMapping
    public ResponseEntity<VacancyResponse> createVacancy(@Valid @RequestBody VacancyRequest request) throws WorkFloException {
        return ResponseEntity.status(CREATED).body(vacancyService.createVacancy(request));
    }
    @PatchMapping
    public ResponseEntity<UpdateVacancyResponse> updateVacancy(@RequestParam("userId") Long userId,
                                                               @RequestParam("id") Long id,
                                                               @RequestBody UpdateVacancyRequest request)
                                                                                throws ProjectOwnerException,
                                                                                       VacancyNotFoundException {
        return ResponseEntity.status(OK).body(vacancyService.updateVacancy(userId, id, request));
    }
    @GetMapping
    public ResponseEntity<List<JoinProjectProjection>> getVacancyRequest(@Valid @RequestBody ViewVacancyRequest request)
                                                                                      throws ProjectOwnerException,
                                                                                             VacancyNotFoundException {
        return ResponseEntity.status(201).body(requestVacancy.getVacancyRequests(request));
    }
}
