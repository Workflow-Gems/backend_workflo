package com.workflo.workflo_backend.join_project.controller;


import com.workflo.workflo_backend.exceptions.WorkFloException;
import com.workflo.workflo_backend.join_project.dtos.request.JoinProjectRequest;
import com.workflo.workflo_backend.join_project.dtos.response.JoinProjectResponse;
import com.workflo.workflo_backend.join_project.services.JoinRequestService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user/project")
@AllArgsConstructor
public class JoinProjectController {

    private final JoinRequestService requestService;
    @PostMapping("/bid")
    public ResponseEntity<JoinProjectResponse> bidForProject(@RequestBody JoinProjectRequest request) throws WorkFloException {
        return ResponseEntity.ok().body(requestService.bidForProject(request));
    }
}
