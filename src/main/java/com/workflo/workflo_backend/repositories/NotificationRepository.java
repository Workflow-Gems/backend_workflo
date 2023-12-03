package com.workflo.workflo_backend.repositories;

import com.workflo.workflo_backend.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
