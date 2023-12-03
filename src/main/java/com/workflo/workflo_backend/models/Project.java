package com.workflo.workflo_backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
@Entity
@RequiredArgsConstructor
@Getter
@Setter
public class Project {
    @Id
    @GeneratedValue
    private Long projectId;
    private String projectTitle;
    @ManyToOne
    private User projectCreator;
    private List<ContributorRequest> requests_bids;
}
