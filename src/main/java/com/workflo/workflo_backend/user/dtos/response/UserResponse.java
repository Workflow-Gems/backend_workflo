package com.workflo.workflo_backend.user.dtos.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
public class UserResponse {
    private Long id;
    private String email;
    private String message;
}
