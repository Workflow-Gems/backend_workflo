package com.workflo.workflo_backend.join_project.dtos.request;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter@Getter
public class JoinProjectRequest {
    @NotNull
    private Long userId;
    @NotNull
    private Long projectId;
    private String message;
}
