package com.workflo.workflo_backend.user.dtos.response;


import lombok.*;


@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter
public class AddressResponse {
    private String city;
    private String state;
    private String country;
    private Long zipCode;
}
