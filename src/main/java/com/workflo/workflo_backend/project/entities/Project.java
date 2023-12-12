package com.workflo.workflo_backend.project.entities;


import com.workflo.workflo_backend.join_project.models.JoinProject;
import com.workflo.workflo_backend.user.models.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@Getter@Setter
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long identifier;
    private String name;
    private String summary;
    private String description;
    @Enumerated(STRING)
    private ProjectCategory category;
    private String image = null;
    @ElementCollection
    private List<String> neededSkills;
    @ManyToOne(fetch = FetchType.LAZY)
    private User creatorId;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "project_members",
               joinColumns = @JoinColumn(name = "project_id"),
               inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> members;
    private LocalDateTime creationMark;
    @Enumerated(STRING)
    private ProjectStatus projectStatus;
    @OneToMany(mappedBy = "project")
    private List<JoinProject> projectRequest;
    @PrePersist
    private void setCreationMark(){
        this.creationMark = LocalDateTime.now();
    }
}
