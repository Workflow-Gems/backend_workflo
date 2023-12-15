package com.workflo.workflo_backend.join_project.dtos.response;


import com.workflo.workflo_backend.join_project.models.JoinProjectStatus;
import com.workflo.workflo_backend.user.dtos.response.FoundUserResponse;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
public class JoinProjectProjection {
    private Long id;
    private FoundUserResponse requester;
    private LocalDateTime requestedDateTime;
    private String message;
    private JoinProjectStatus status;
}
