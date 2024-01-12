package com.workflo.workflo_backend.user.services;

import com.workflo.workflo_backend.exceptions.*;
import com.workflo.workflo_backend.project.dtos.response.ProjectProjection;
import com.workflo.workflo_backend.project.dtos.response.ProjectResponse;
import com.workflo.workflo_backend.user.dtos.request.*;
import com.workflo.workflo_backend.user.dtos.response.FoundUserResponse;
import com.workflo.workflo_backend.user.dtos.response.UserResponse;
import com.workflo.workflo_backend.user.models.Profile;
import com.workflo.workflo_backend.user.models.User;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface UserService {
    UserResponse createUser(UserRequest request) throws SendMailException;
    String createAddress(AddressRequest request) throws UserNotFoundException;
    String createProfile(ProfileRequest request) throws UserNotFoundException, CloudUploadException, UserNotVerifiedException;
    String confirmToken(String email, String token) throws TokenExceptions, SendMailException;
    default ProfileRequest buildProfileRequest(MultipartFile image,
                                               Long id,
                                               Map<String, String> portfolio,
                                               String about,
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
    default ProfileRequest buildProfileRequest(Long id,
                                               Map<String, String> portfolio,
                                               String about,
                                               String jobTitle,
                                               List<String> skills){
        ProfileRequest request = new ProfileRequest();
        request.setUserId(id);
        request.setAbout(about);
        request.setSkills(skills);
        request.setPortfolio(portfolio);
        request.setJobTitle(jobTitle);
        return request;
    }
    FoundUserResponse findProjectedUserById(Long id) throws UserNotFoundException;
    FoundUserResponse getUserById(Long id) throws UserNotFoundException;
    User getUserWithId(Long id) throws UserNotFoundException;

    UserResponse updateUser(Long id, UpdateUserRequest request) throws UserNotFoundException, UpdateNotAllowedException;

    String uploadProfilePicture(long id, MultipartFile multipart) throws UserNotFoundException, CloudUploadException;

    List<ProjectResponse> viewJoinedProjectsByUser(long id) throws UserNotFoundException, ProjectNotExistException;

    List<Profile> searchByJobTitleOrSkills(String jobTitleOrSkill);


}
