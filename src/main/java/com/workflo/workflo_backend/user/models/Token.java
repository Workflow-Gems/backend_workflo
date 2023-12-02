package com.workflo.workflo_backend.user.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Setter@Getter
public class Token {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String token;
    @ManyToOne(cascade = ALL)
    @JoinColumn(name = "user_token", referencedColumnName = "id")
    private User user;
    private LocalTime timeCreated;
    private LocalTime expiryTime;
    private LocalTime confirmAt;
//    private
}
