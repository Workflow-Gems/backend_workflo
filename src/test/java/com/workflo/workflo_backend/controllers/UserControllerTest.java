package com.workflo.workflo_backend.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.workflo.workflo_backend.exceptions.CloudUploadException;
import com.workflo.workflo_backend.user.dtos.request.AddressRequest;
import com.workflo.workflo_backend.user.dtos.request.UserRequest;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

import static com.workflo.workflo_backend.services.app.CloudServiceTest.createMultipart;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
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
        userRequest.setEmail("leumasre@mail.com");
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
                .userId(2L).city("   ").country(null)
                .zipCode("12345").state("state").build();
        mockMvc.perform(post("/api/v1/user/address")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }
    @Test
    public void confirmToken() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String email = "leumasre@mail.com";
        String token = "1f1b35a7-5421-40a1-9a74-77c846b5457e";
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/confirm")
                                .param("email", email)
                                .param("token", token))
                                .andExpect(status().is2xxSuccessful())
                                .andDo(print());
    }
    @Test
    public void createProfileA() throws CloudUploadException, Exception {
        List<String> list = List.of("skills");
        Map<String, String> map = Map.of("key", "value");

        String parsed = JSONObject.toJSONString(map);

        MultipartFile file = createMultipart();

        mockMvc.perform(multipart("/api/v1/user/profile")
                        .file(new MockMultipartFile("image", file.getInputStream()))
                        .part(new MockPart("id", new byte[]{49}))
                        .part(new MockPart("about", "about".getBytes()))
                        .part(new MockPart("portfolio", parsed.getBytes()))
                        .part(new MockPart("jobTitle", "job-title".getBytes()))
                        .part(new MockPart("skills", list.toString().getBytes()))
                        .contentType(MULTIPART_FORM_DATA_VALUE))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }
    @Test
    public void createProfile() throws CloudUploadException, Exception {
        List<String> list = List.of("skills");
        Map<String, String> map = Map.of("key", "value");

        String parsed = JSONObject.toJSONString(map);

        mockMvc.perform(multipart("/api/v1/user/profile")
                        .part(new MockPart("id", new byte[]{49}))
                        .part(new MockPart("about", "about".getBytes()))
                        .part(new MockPart("portfolio", parsed.getBytes()))
                        .part(new MockPart("jobTitle", "job-title".getBytes()))
                        .part(new MockPart("skills", list.toString().getBytes()))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }
    @Test
    public void getUserByIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/1"))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }
}
