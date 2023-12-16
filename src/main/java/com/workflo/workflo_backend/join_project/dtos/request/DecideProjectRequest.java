package com.workflo.workflo_backend.join_project.dtos.request;


import com.workflo.workflo_backend.join_project.models.JoinProjectStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
public class DecideProjectRequest {
    @NotNull@NotBlank@NotEmpty
    private JoinProjectStatus status;
    @NotNull
    private Long userId;
    @NotNull
    private Long requestId;
}
