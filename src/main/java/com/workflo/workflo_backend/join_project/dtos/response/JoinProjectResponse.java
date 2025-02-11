package com.workflo.workflo_backend.join_project.dtos.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.workflo.workflo_backend.join_project.models.JoinProjectStatus;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JoinProjectResponse {
    private Long id;
    private String message;
    private JoinProjectStatus status;
}
