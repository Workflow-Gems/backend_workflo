package com.workflo.workflo_backend.vacancy.entity;


import com.workflo.workflo_backend.join_project.models.JoinProject;
import com.workflo.workflo_backend.project.entities.Project;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
public class Vacancy {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @OneToOne
    private Project project;
    @ElementCollection
    private List<String> neededSkills;
    private String text;
    @CreationTimestamp
    private LocalDateTime creationDateTime;
    @Enumerated(STRING)
    private VacancyStatus status;
}
