package com.workflo.workflo_backend.project.entities;

import com.workflo.workflo_backend.user.models.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
 @Setter
@NoArgsConstructor
@AllArgsConstructor

public class Invitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    //@ManyToOne
    //@JoinColumn(name = "user_id")
    //private User user;
    private Long contributorId; // private User contributor


    //@ManyToOne
    //@JoinColumn(name = "project_id")
    //private Project project;

    private Long projectId;
    private String message;
    private boolean isAccepted;

    private  String projectLink;

}
