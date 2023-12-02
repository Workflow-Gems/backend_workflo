package com.workflo.workflo_backend.user.services;

import com.workflo.workflo_backend.exceptions.CloudUploadException;
import com.workflo.workflo_backend.exceptions.SendMailException;
import com.workflo.workflo_backend.exceptions.TokenExceptions;
import com.workflo.workflo_backend.exceptions.UserNotFoundException;
import com.workflo.workflo_backend.user.dtos.request.AddressRequest;
import com.workflo.workflo_backend.user.dtos.request.ProfileRequest;
import com.workflo.workflo_backend.user.dtos.request.UserRequest;
import com.workflo.workflo_backend.user.dtos.response.UserResponse;

public interface UserService {

    UserResponse createUser(UserRequest request) throws SendMailException;
    String createAddress(AddressRequest request) throws UserNotFoundException;

    String createProfile(ProfileRequest request) throws UserNotFoundException, CloudUploadException;

    String confirmToken(String email, String token) throws TokenExceptions;
}
