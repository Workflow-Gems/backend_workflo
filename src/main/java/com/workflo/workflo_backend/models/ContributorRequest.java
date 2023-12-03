package com.workflo.workflo_backend.models;


import jakarta.persistence.*;

@Entity
public class ContributorRequest {
    @Id
    @GeneratedValue
    private Long requestId;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User contributor;
    @ManyToOne
    @JoinColumn(name="project_id")
    private Project project;
    @Enumerated(EnumType.STRING)
    private RequestStatus status;
}
