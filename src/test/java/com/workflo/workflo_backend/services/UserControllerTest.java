package com.workflo.workflo_backend.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.workflo.workflo_backend.user.dtos.request.UserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testUserController() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        UserRequest userRequest = new UserRequest();
        userRequest.setFirstName("firstName");
        userRequest.setPhoneNumber("09063587905");
        userRequest.setPassword("Password12@");
        userRequest.setEmail("email@mail.com");
        userRequest.setLastName("lastName");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/register")
                .content(mapper.writeValueAsString(userRequest))
                .contentType(APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }


}
