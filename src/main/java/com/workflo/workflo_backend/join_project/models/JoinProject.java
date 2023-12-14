package com.workflo.workflo_backend.join_project.models;

import com.workflo.workflo_backend.user.dtos.response.FoundUserResponse;
import com.workflo.workflo_backend.user.models.User;
import com.workflo.workflo_backend.vacancy.entity.Vacancy;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Setter@Getter
@Builder
public class JoinProject {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @ManyToOne
    private Vacancy vacancy;
    @ManyToOne
    private User user;
    private String message;
    @CreationTimestamp
    private LocalDateTime requestedDateTime;
    @Enumerated(STRING)
    private JoinProjectStatus status;
}
