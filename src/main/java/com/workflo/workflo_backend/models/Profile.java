package com.workflo.workflo_backend.models;

import jakarta.persistence.*;

import java.util.List;
@Entity
public class Profile {
    @Id
    @GeneratedValue
    private Long userId;
    @ElementCollection
    private List<String> skills;
    private int yearsOfExperience;
    @OneToOne
    @JoinColumn(name="userId")
    private User user;
}
