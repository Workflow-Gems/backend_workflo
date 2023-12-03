package com.workflo.workflo_backend.models;

import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Entity
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private int userId;
    private String name;
    @Column(unique=true)
    private String email;
    private String password;
    @OneToMany(mappedBy="creator")
    private List<Project> createdProjects;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="userId")
    private Profile profile;


}
