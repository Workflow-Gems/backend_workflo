package com.workflo.workflo_backend.appConfig.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter@Setter
public class MailRequest {
    @JsonProperty("sender")
    private final To sender;
    @JsonProperty("to")
    private List<To> to;
    private String subject;
    private String htmlContent;
    public MailRequest(){
        this.sender = new To("WorkFlo inc...", "leumasre@gmail.com");
    }
}
