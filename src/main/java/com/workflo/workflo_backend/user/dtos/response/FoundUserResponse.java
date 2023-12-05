package com.workflo.workflo_backend.user.dtos.response;


import lombok.*;


@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
public class FoundUserResponse {
    private String firstName;
    private String lastName;
    private String email;
    private Long id;
    private AccountResponse account;
}
