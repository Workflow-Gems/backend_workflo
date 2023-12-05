package com.workflo.workflo_backend.user.models;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

import static jakarta.persistence.GenerationType.IDENTITY;


@Entity
@Getter@Setter
public class Profile {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
//    @Column(name = "image", length = 1000)
    private String image;
//    @Column(name = "about", length = 500)
    private String about;
//    @Column(name = "job_title", updatable = true, length = 100)
    private String jobTitle;
    @ElementCollection
    private List<String> skills;
    @ElementCollection
//    @MapKeyJoinColumn(name = "portfolio_title")
    private Map<String, String> portFolio;
}
