package com.workflo.workflo_backend.user.services;

import com.workflo.workflo_backend.exceptions.CloudUploadException;
import com.workflo.workflo_backend.exceptions.SendMailException;
import com.workflo.workflo_backend.exceptions.TokenExceptions;
import com.workflo.workflo_backend.exceptions.UserNotFoundException;
import com.workflo.workflo_backend.user.dtos.request.*;
import com.workflo.workflo_backend.user.dtos.response.UserResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UserService {

    UserResponse createUser(UserRequest request) throws SendMailException;
    String createAddress(AddressRequest request) throws UserNotFoundException;

    String createProfile(ProfileRequest request) throws UserNotFoundException, CloudUploadException;

    String confirmToken(String email, String token) throws TokenExceptions;


    default ProfileRequest buildProfileRequest(MultipartFile image,
                                               Long id, Map<String, String> portfolio, String about,
                                               String jobTitle,
                                               List<String> skills){
        ProfileRequest request = new ProfileRequest();
        request.setImage(image);
        request.setUserId(id);
        request.setAbout(about);
        request.setSkills(skills);
        request.setPortfolio(portfolio);
        request.setJobTitle(jobTitle);
        return request;
    }
}
