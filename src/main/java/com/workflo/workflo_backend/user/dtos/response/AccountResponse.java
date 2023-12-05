package com.workflo.workflo_backend.user.dtos.response;

import lombok.*;


@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
public class AccountResponse {
    private String email;
    private String phoneNumber;
    private ProfileResponse profile;
    private AddressResponse address;
}
