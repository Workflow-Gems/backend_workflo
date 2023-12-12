package com.workflo.workflo_backend.user.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
public class Address {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long identifier;
//    @Column(name = "city", length = 100)
    private String city;
//    @Column(length = 100)
    private String state;
//    @Column(length = 100)
    private String country;
//    @Column(length = 10)
    private Long zipCode;
}
