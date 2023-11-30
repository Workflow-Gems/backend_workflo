package com.workflo.workflo_backend.user.dtos.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter@Setter
public class AddressRequest {
    private Long userId;
    @NotNull(message = "city field cannot be empty")
    @NotNull
    @NotEmpty
    @NotBlank
    private String city;
    private String country;
    private String state;
    private String zipCode;
}
