package com.workflo.workflo_backend.join_project.dtos.response;


import com.workflo.workflo_backend.join_project.models.JoinProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
public class DecideProjectResponse {
    private JoinProjectStatus status;
    private Long projectMembers;
}
