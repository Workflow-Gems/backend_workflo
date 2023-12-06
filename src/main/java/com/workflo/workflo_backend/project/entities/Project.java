package com.workflo.workflo_backend.project.entities;


import com.workflo.workflo_backend.user.models.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@Getter@Setter
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private String summary;
    private String description;
    @Enumerated(STRING)
    private ProjectCategory category;
    private String image = null;
    @ElementCollection
    private List<String> neededSkills;
    @ManyToOne
    private User creatorId;
    @ManyToMany
    @JoinTable(name = "project_members",
               joinColumns = @JoinColumn(name = "project_id"),
               inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> members;
    private LocalTime createdTime;
    private LocalDate dateCreated;
    @Enumerated(STRING)
    private ProjectStatus projectStatus;
}
