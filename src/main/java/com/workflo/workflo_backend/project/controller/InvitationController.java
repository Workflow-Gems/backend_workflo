package com.workflo.workflo_backend.project.controller;

import com.workflo.workflo_backend.project.service.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user/invitation")
@RequiredArgsConstructor

public class InvitationController {
    private  final InvitationService invitationService;

    @PostMapping("/sendinvitation")
    public ResponseEntity<String> sendInvitation(@RequestParam Long contributorUserId, @RequestParam Long projectId, @RequestParam String message, @RequestParam Long creatorId){
        return new ResponseEntity<>(invitationService.sendInvitation(contributorUserId,projectId,message,creatorId), HttpStatus.OK);
    }

}
