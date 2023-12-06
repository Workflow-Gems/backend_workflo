package com.workflo.workflo_backend.user.models;

import com.workflo.workflo_backend.join_project.dtos.request.JoinProjectRequest;
import com.workflo.workflo_backend.join_project.models.JoinProject;
import com.workflo.workflo_backend.project.entities.Project;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;


@Entity
@Getter@Setter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(length = 100)
    private String lastName;
    @Column(length = 100)
    private String firstName;
    @Column(unique = true, nullable = false)
    private String email;
    @OneToOne(cascade = ALL, fetch = EAGER)
    @JoinColumn(name = "user_account",
            referencedColumnName = "id")
    private  Account account;
    @CreationTimestamp
    private LocalDateTime joinedDateTime;
    @Enumerated(STRING)
    private UserStatus userStatus;
    @Column(name = "is_enabled")
    private boolean enabled = false;
    @OneToMany(mappedBy = "creatorId", cascade = ALL, fetch = LAZY)
    private Set<Project> createdProjects;
    @ManyToMany(mappedBy = "members", cascade = ALL, fetch = LAZY)
    private Set<Project> joinedProjects;
    @OneToMany(mappedBy = "user")
    private List<JoinProject> requestedProject;
}
