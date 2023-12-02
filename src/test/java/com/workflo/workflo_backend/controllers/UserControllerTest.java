package com.workflo.workflo_backend.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.workflo.workflo_backend.user.dtos.request.AddressRequest;
import com.workflo.workflo_backend.user.dtos.request.UserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

        mockMvc.perform(post("/api/v1/register")
                .content(mapper.writeValueAsString(userRequest))
                .contentType(APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }



    @Test
    public void createAddressTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        AddressRequest request = AddressRequest
                .builder()
                .userId(1L)
                .city("city")
                .country("country")
                .zipCode("12345")
                .state("state")
                .build();
        mockMvc.perform(post("/api/v1/user/address")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    public void createAddress() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        AddressRequest request = AddressRequest
                .builder()
                .userId(2L).city("   ").country("country")
                .zipCode("12345").state("state").build();
        mockMvc.perform(post("/api/v1/user/address")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }
}
