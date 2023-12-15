package com.workflo.workflo_backend.join_project.dtos.request;


import com.workflo.workflo_backend.join_project.models.JoinProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
public class DecideProjectRequest {
    private JoinProjectStatus status;
    private Long userId;
    private Long requestId;
}
