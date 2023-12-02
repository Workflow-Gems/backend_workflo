package com.workflo.workflo_backend.user.controller;


import com.workflo.workflo_backend.exceptions.SendMailException;
import com.workflo.workflo_backend.exceptions.UserNotFoundException;
import com.workflo.workflo_backend.user.dtos.request.AddressRequest;
import com.workflo.workflo_backend.user.dtos.request.UserRequest;
import com.workflo.workflo_backend.user.dtos.response.UserResponse;
import com.workflo.workflo_backend.user.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class UserController {


    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest request) throws SendMailException {
        return ResponseEntity.status(CREATED).body(userService.createUser(request));
    }
    @PostMapping("/user/address")
    public ResponseEntity<String> createAddress(@Valid @RequestBody AddressRequest request) throws UserNotFoundException {
        return ResponseEntity.ok().body(userService.createAddress(request));
    }
}
