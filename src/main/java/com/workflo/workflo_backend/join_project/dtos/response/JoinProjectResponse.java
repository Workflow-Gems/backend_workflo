package com.workflo.workflo_backend.join_project.dtos.response;


import com.workflo.workflo_backend.join_project.models.JoinProjectStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter@Setter
public class JoinProjectResponse {
    private Long id;
    private String message;
    private JoinProjectStatus status;
}
