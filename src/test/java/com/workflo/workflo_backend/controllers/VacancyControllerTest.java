package com.workflo.workflo_backend.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.workflo.workflo_backend.vacancy.dtos.request.UpdateVacancyRequest;
import com.workflo.workflo_backend.vacancy.dtos.request.VacancyRequest;
import com.workflo.workflo_backend.vacancy.entity.VacancyStatus;
import com.workflo.workflo_backend.vacancy.services.VacancyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.workflo.workflo_backend.vacancy.entity.VacancyStatus.CLOSED;
import static com.workflo.workflo_backend.vacancy.entity.VacancyStatus.OPEN;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VacancyControllerTest {

    @Autowired
    private VacancyService vacancyService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createVacancyTest() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        VacancyRequest request = new VacancyRequest();
        request.setText("apply within");
        request.setNeededSkills(List.of("tester"));
        request.setUserIdentifier(1L);
        request.setProjectIdentifier(2L);
        mockMvc.perform(post("/api/v1/user/project/vacancy")
                    .content(mapper.writeValueAsString(request))
                    .contentType(APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    public void updateVacancyTest() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        UpdateVacancyRequest request = new UpdateVacancyRequest();
        request.setStatus(OPEN);
        mockMvc.perform(patch("/api/v1/user/project/vacancy")
                        .param("userId", "1")
                        .param("id", "1")
                        .content(mapper.writeValueAsString(request))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }
}
