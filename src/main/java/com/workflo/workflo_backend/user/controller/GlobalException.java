package com.workflo.workflo_backend.user.controller;


import com.workflo.workflo_backend.exceptions.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static java.time.LocalDate.now;
import static org.springframework.http.HttpStatus.BAD_GATEWAY;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestControllerAdvice
public class GlobalException {
    @ResponseStatus(BAD_GATEWAY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidationError(MethodArgumentNotValidException exception){
        Map<String, String> error = new HashMap<>();
        exception.getFieldErrors().forEach(e ->
                error.put(e.getField(), e.getDefaultMessage())
        );
        ErrorMessage message = ErrorMessage.builder()
                .message(error).status(BAD_GATEWAY)
                .date(now()).time(LocalTime.now()).build();
        return ResponseEntity.badRequest().body(message);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleUSerException(UserNotFoundException exception){
        Map<String, String> error = new HashMap<>();
        error.put("message", exception.getMessage());
        ErrorMessage message = ErrorMessage.builder()
                .message(error).status(BAD_GATEWAY)
                .date(now()).time(LocalTime.now()).build();
        return ResponseEntity.badRequest().body(message);
    }

    @ResponseStatus(BAD_GATEWAY)
    @ExceptionHandler(DuplicatedUserEmailException.class)
    public ResponseEntity<ErrorMessage> duplicateUser(DuplicatedUserEmailException exception){
        Map<String, String> error = new HashMap<>();
        error.put("error-message", exception.getMessage());
        return ResponseEntity.badRequest().body(new ErrorMessage(
                error,
                BAD_GATEWAY,
                now(),
                LocalTime.now()
        ));
    }

    @ResponseStatus(BAD_GATEWAY)
    @ExceptionHandler(SendMailException.class)
    public ResponseEntity<ErrorMessage> mailerException(SendMailException exception){
        Map<String, String> error = new HashMap<>();
        error.put("error-message", exception.getMessage());
        return ResponseEntity.badRequest().body(new ErrorMessage(
                error,
                BAD_GATEWAY,
                now(),
                LocalTime.now()
        ));
    }

    @ResponseStatus(BAD_GATEWAY)
    @ExceptionHandler(CloudUploadException.class)
    public ResponseEntity<ErrorMessage> cloudUpload(CloudUploadException exception){
        Map<String, String> error = new HashMap<>();
        error.put("error-message", exception.getMessage());
        return ResponseEntity.badRequest().body(new ErrorMessage(
                error,
                BAD_GATEWAY,
                now(),
                LocalTime.now()
        ));
    }

    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(TokenExceptions.class)
    public ResponseEntity<ErrorMessage> tokenError(TokenExceptions exceptions){
        ErrorMessage errorMessage = new ErrorMessage(
                Map.of("message", exceptions.getMessage()),
                FORBIDDEN,
                now(),
                LocalTime.now()
        );
        return ResponseEntity.badRequest().body(errorMessage);
    }
}
