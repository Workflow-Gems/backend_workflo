package com.workflo.workflo_backend.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;


@AllArgsConstructor
@Getter@Setter@Builder
public class ErrorMessage {
    private Map<String, String> message;
    private HttpStatus status;
    private LocalDate date;
    private LocalTime time;
}
