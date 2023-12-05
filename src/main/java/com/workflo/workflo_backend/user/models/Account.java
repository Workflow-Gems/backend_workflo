package com.workflo.workflo_backend.user.models;


import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@ToString
@Getter@Setter
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
//    @Column(length = 100, nullable = false, unique = true)
    private String email;
//    @Column(length = 2000, nullable = false)
    private String password;
//    @Column(length = 15, nullable = false)
    private String phoneNumber;
    @OneToOne(cascade = ALL)
    @JoinColumn(name = "account_profile",
            referencedColumnName = "id")
    private Profile profile;

    @OneToOne(cascade = ALL, fetch = EAGER)
    @JoinColumn(name = "user_address",
            referencedColumnName = "id")
    private Address address;
}
