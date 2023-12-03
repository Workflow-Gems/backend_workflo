package com.workflo.workflo_backend.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class Notification {
    @Id
    @GeneratedValue
    private Long notificationId;

    @ManyToOne
    @JoinColumn(name="userId")
    private User recipient;

    private String message;
}
