package com.workflo.workflo_backend.join_project.controller;


import com.workflo.workflo_backend.exceptions.DuplicateProjectMemberException;
import com.workflo.workflo_backend.exceptions.ProjectOwnerException;
import com.workflo.workflo_backend.exceptions.WorkFloException;
import com.workflo.workflo_backend.join_project.dtos.request.DecideProjectRequest;
import com.workflo.workflo_backend.join_project.dtos.request.JoinProjectRequest;
import com.workflo.workflo_backend.join_project.dtos.response.DecideProjectResponse;
import com.workflo.workflo_backend.join_project.dtos.response.JoinProjectResponse;
import com.workflo.workflo_backend.join_project.services.JoinRequestService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/project")
@AllArgsConstructor
public class JoinProjectController {

    private final JoinRequestService requestService;
    @PostMapping("/bid")
    public ResponseEntity<JoinProjectResponse> bidForProject(@Valid @RequestBody JoinProjectRequest request) throws WorkFloException {
        return ResponseEntity.ok().body(requestService.bidForProject(request));
    }

    @PatchMapping("/bid")
    public ResponseEntity<DecideProjectResponse> decideBidRequest(@Valid @RequestBody DecideProjectRequest request)
                                                                                throws DuplicateProjectMemberException,
                                                                                       ProjectOwnerException {
        return ResponseEntity.ok().body(requestService.decideBidRequest(request));
    }
    @GetMapping("bid/{id}")
    public ResponseEntity<List<JoinProjectResponse>> checkUserRequest(@PathVariable Long id){
        return ResponseEntity.ok().body(requestService.findUserRequestById(id));
    }
}
