package com.workflo.workflo_backend.project.repository;

import com.workflo.workflo_backend.project.entities.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
}
