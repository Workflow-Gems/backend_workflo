package com.workflo.workflo_backend.user.dtos.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter@Setter
public class AddressRequest {
    @NotNull(message = "id field cannot be empty...")
//    @NotEmpty(message = "id field cannot be empty...")
//    @NotBlank(message = "id field cannot be blank...")
    private Long userId;
    @NotNull(message = "city field cannot be empty...")
    @NotEmpty(message = "city field cannot be empty...")
    @NotBlank(message = "city field cannot be blank...")
    private String city;
    @NotNull(message = "country field cannot be empty...")
    @NotEmpty(message = "country field cannot be empty...")
    @NotBlank(message = "country field cannot be blank...")
    private String country;
    @NotNull(message = "state field cannot be empty...")
    @NotEmpty(message = "state field cannot be empty...")
    @NotBlank(message = "state field cannot be blank...")
    private String state;
    @NotNull(message = "zipcode field cannot be empty...")
    @NotEmpty(message = "zipcode field cannot be empty...")
    @NotBlank(message = "zipcode field cannot be blank...")
    private String zipCode;
}
