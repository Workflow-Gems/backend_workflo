package com.workflo.workflo_backend.request;

import com.workflo.workflo_backend.user.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
@RequiredArgsConstructor
public class CreateProjectRequest {
    private User projectCreator;
    private String projectTitle;
    private String projectDescription;
    private LocalDate StartDate;
    private LocalDate endDate;
    private List<User> contributors;
}
