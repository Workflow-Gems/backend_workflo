package com.workflo.workflo_backend.response;

import com.workflo.workflo_backend.user.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class CreateProjectResponse {
    private Long projectId;
    private String projectTitle;
    private User projectCreator;
}
