package com.workflo.workflo_backend.user.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter
public class AccountRequest {

//    @NotNull(message = "email address cannot be empty")
//    @NotBlank(message = "white spaces not allow in email field")
//    @Email(message = "invalid email format")
//    @NotEmpty(message = "email cannot be empty")
    private String email;
    //    @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W]).{8,64})",
//            message = "Password must be at least 8 characters long, must also contain one upper case, " +
//                    "one lower case, one special character and a digit")
//    @NotNull(message = "password cannot be blank")
    private String password;
    //    @Pattern(regexp = "[(]?\\d{3}[)]?\\s?-?\\s?\\d{3}\\s?-?\\s?\\d{4}", message = "enter valid number")
    private String phoneNumber;
}
