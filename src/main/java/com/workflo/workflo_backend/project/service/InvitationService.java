package com.workflo.workflo_backend.project.service;

public interface InvitationService {
    String sendInvitation(Long contributorUserId, Long projectId, String message,Long creatorId);
}
