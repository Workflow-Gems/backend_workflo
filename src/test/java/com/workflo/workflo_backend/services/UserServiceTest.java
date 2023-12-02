package com.workflo.workflo_backend.services;


import com.workflo.workflo_backend.exceptions.*;
import com.workflo.workflo_backend.user.dtos.request.AddressRequest;
import com.workflo.workflo_backend.user.dtos.request.ProfileRequest;
import com.workflo.workflo_backend.user.dtos.request.UserRequest;
import com.workflo.workflo_backend.user.dtos.response.UserResponse;
import com.workflo.workflo_backend.user.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static com.workflo.workflo_backend.services.app.CloudServiceTest.createMultipart;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@Slf4j
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void createUserTest() throws SendMailException {
        UserRequest request = new UserRequest();
        request.setFirstName("firstName");
        request.setLastName("lastName");
        request.setEmail("aiyeboy@gmail.com");
        request.setPassword("Password12@");
        request.setPhoneNumber("08083587905");
        UserResponse response = userService.createUser(request);
        assertThat(response).isNotNull();
        log.info("id :: {}",response.getId());
        assertThat(response.getId()).isNotNull();
    }
    @Test
    public void duplicateEmailNotAllowed(){
        UserRequest request = new UserRequest();
        request.setFirstName("firstName");
        request.setLastName("lastName");
        request.setEmail("leumasre@gmail.com");
        request.setPassword("Password12@");
        request.setPhoneNumber("08063587905");
        assertThrows(DuplicatedUserEmailException.class, ()->userService.createUser(request));
    }

    @Test
    public void createAddress() throws UserNotFoundException {
        AddressRequest request = AddressRequest
               .builder()
               .userId(1L)
               .city("city")
               .country("country")
               .zipCode("12345")
               .state("state")
               .build();
        String response = userService.createAddress(request);
        log.info("response :: {}", response);
        assertThat(response).isNotNull();
    }

    @Test
    public void profileCannotBeCreatedIfNoAccount() throws CloudUploadException, UserNotFoundException {
        ProfileRequest request = new ProfileRequest();
        request.setUserId(1L);
        request.setAbout("about");
        request.setSkills(List.of("skills"));
        request.setImage(createMultipart());
        request.setPortfolio(Map.of("k", "v"));

        String response = userService.createProfile(request);
        log.info("response :: {}", response);
        assertThat(response).isNotNull();
    }

    @Test
    public void confirmToken() throws TokenExceptions {
        String email = "pijog53310@frandin.com";
        String token = "735fc9fa-2208-4ddb-8b06-b3f70e21cd7c";
        String response = userService.confirmToken(email, token);
        assertThat(response).isNotNull();
    }
}
