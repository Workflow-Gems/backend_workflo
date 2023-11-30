package com.workflo.workflo_backend.appConfig.dtos.response;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter@Getter
public class MailResponse {
    private String message;
    private Long statusCode;
}
