package com.workflo.workflo_backend.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.workflo.workflo_backend.join_project.dtos.request.DecideProjectRequest;
import com.workflo.workflo_backend.join_project.dtos.request.JoinProjectRequest;
import com.workflo.workflo_backend.join_project.services.JoinRequestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static com.workflo.workflo_backend.join_project.models.JoinProjectStatus.ACCEPTED;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class JoinProjectRequestControllerTest {
    @Autowired
    private JoinRequestService requestService;
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void bidControllerTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JoinProjectRequest request = new JoinProjectRequest(2L, 4L, "abeg");
        mockMvc.perform(post("/api/v1/user/project/bid")
                        .content(mapper.writeValueAsString(request))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andDo(print()).andReturn();
    }
    @Test
    public void bidControllerTestA() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JoinProjectRequest request = new JoinProjectRequest(3L, 4L, "abeg");
        mockMvc.perform(post("/api/v1/user/project/bid")
                        .content(mapper.writeValueAsString(request))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print()).andReturn();
    }
    @Test
    public void decideBidRequest() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        DecideProjectRequest request = new DecideProjectRequest();
        request.setUserId(1L);
        request.setRequestId(10L);
        request.setStatus(ACCEPTED);
        mockMvc.perform(patch("/api/v1/user/project/bid")
                        .content(mapper.writeValueAsString(request))
                        .contentType(APPLICATION_JSON))
               .andExpect(status().is2xxSuccessful())
               .andDo(print());
    }
    @Test
    public void userCanGetListOfAllRequestedProjects() throws Exception{
        mockMvc.perform(get("/api/v1/user/project/bid/2"))
               .andExpect(status().is(200)).andDo(print());
    }
}
