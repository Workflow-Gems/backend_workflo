package com.workflo.workflo_backend.user.controller;


import com.workflo.workflo_backend.exceptions.CloudUploadException;
import com.workflo.workflo_backend.exceptions.SendMailException;
import com.workflo.workflo_backend.exceptions.TokenExceptions;
import com.workflo.workflo_backend.exceptions.UserNotFoundException;
import com.workflo.workflo_backend.user.dtos.request.AddressRequest;
import com.workflo.workflo_backend.user.dtos.request.ProfileRequest;
import com.workflo.workflo_backend.user.dtos.request.UserRequest;
import com.workflo.workflo_backend.user.dtos.response.UserResponse;
import com.workflo.workflo_backend.user.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class UserController {


    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest request)
                                                                           throws SendMailException {
        return ResponseEntity.status(CREATED).body(userService.createUser(request));
    }
    @PostMapping("/user/address")
    public ResponseEntity<String> createAddress(@Valid @RequestBody AddressRequest request)
                                                                            throws UserNotFoundException {
        return ResponseEntity.ok().body(userService.createAddress(request));
    }
    @GetMapping("/user/confirm")
    public ResponseEntity<String> confirmUserEmail(@RequestParam("email") String email,
                                                   @RequestParam("token") String token)
                                                                            throws TokenExceptions,
                                                                                   SendMailException {
        return ResponseEntity.ok().body(userService.confirmToken(email, token));
    }
    @PostMapping(path = "/user/profile",consumes = MULTIPART_FORM_DATA_VALUE )
    public ResponseEntity<String> createProfile(@RequestParam("image") MultipartFile image,
                                                @RequestParam("id") Long id,
                                                @RequestParam Map<String, String> portfolio,
                                                @RequestParam("about") String about,
                                                @RequestParam("jobTitle") String jobTitle,
                                                @RequestParam("skills") List<String> skills)
                                                                                throws UserNotFoundException,
                                                                                       CloudUploadException {
        ProfileRequest request = userService.buildProfileRequest(image, id,portfolio, about, jobTitle, skills);
        String response = userService.createProfile(request);
        return ResponseEntity.ok().body(response);
    }
}
