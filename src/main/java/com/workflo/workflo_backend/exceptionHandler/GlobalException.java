package com.workflo.workflo_backend.exceptionHandler;


import com.workflo.workflo_backend.exceptions.*;
import jakarta.servlet.ServletException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.time.LocalDate;
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
                LocalDate.now(),
                LocalTime.now()
        );
        return ResponseEntity.badRequest().body(errorMessage);
    }
    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(UserNotVerifiedException.class)
    public ResponseEntity<ErrorMessage> unverified(UserNotVerifiedException exceptions){
        ErrorMessage errorMessage = new ErrorMessage(
                Map.of("message", exceptions.getMessage()),
                FORBIDDEN,
                now(),
                LocalTime.now()
        );
        return ResponseEntity.badRequest().body(errorMessage);
    }
    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(ProjectNotExistException.class)
    public ResponseEntity<ErrorMessage> unverified(ProjectNotExistException exceptions){
        ErrorMessage errorMessage = new ErrorMessage(
                Map.of("message", exceptions.getMessage()),
                FORBIDDEN,
                now(),
                LocalTime.now()
        );
        return ResponseEntity.badRequest().body(errorMessage);
    }
    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(ProjectAndUserNotMatchException.class)
    public ResponseEntity<ErrorMessage> projectNotMatch(ProjectAndUserNotMatchException exceptions){
        ErrorMessage errorMessage = new ErrorMessage(
                Map.of("message", exceptions.getMessage()),
                FORBIDDEN,
                now(),
                LocalTime.now()
        );
        return ResponseEntity.badRequest().body(errorMessage);
    }
    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(ProjectOwnerException.class)
    public ResponseEntity<ErrorMessage> projectOwner(ProjectOwnerException exceptions){
        ErrorMessage errorMessage = new ErrorMessage(
                Map.of("message", exceptions.getMessage()),
                FORBIDDEN,
                now(),
                LocalTime.now()
        );
        return ResponseEntity.badRequest().body(errorMessage);
    }
    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(VacancyNotCreatedException.class)
    public ResponseEntity<ErrorMessage> vacancyError(VacancyNotCreatedException exception){
        ErrorMessage errorMessage = new ErrorMessage(
                Map.of("message", exception.getMessage()),
                FORBIDDEN,
                now(),
                LocalTime.now()
        );
        return ResponseEntity.status(415).body(errorMessage);
    }
    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(UpdateNotAllowedException.class)
    public ResponseEntity<ErrorMessage> vacancyError(UpdateNotAllowedException exception){
        ErrorMessage errorMessage = new ErrorMessage(
                Map.of("message", exception.getMessage()),
                FORBIDDEN,
                now(),
                LocalTime.now()
        );
        return ResponseEntity.status(415).body(errorMessage);
    }
@ResponseStatus(FORBIDDEN)
    @ExceptionHandler(VacancyNotFoundException.class)
    public ResponseEntity<ErrorMessage> vacancyError(VacancyNotFoundException exception){
        ErrorMessage errorMessage = new ErrorMessage(
                Map.of("message", exception.getMessage()),
                FORBIDDEN,
                now(),
                LocalTime.now()
        );
        return ResponseEntity.status(415).body(errorMessage);
    }
    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(BidRequestNotAllowedException.class)
    public ResponseEntity<ErrorMessage> bidRequestNotAllowed(BidRequestNotAllowedException exception){
        ErrorMessage errorMessage = new ErrorMessage(
                Map.of("message", exception.getMessage()),
                FORBIDDEN,
                now(),
                LocalTime.now()
        );
        return ResponseEntity.status(415).body(errorMessage);
    }
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateProjectMemberException.class)
    public ResponseEntity<ErrorMessage> duplicateProjectMember(DuplicateProjectMemberException exception){
        ErrorMessage errorMessage = new ErrorMessage(
                Map.of("message", exception.getMessage()),
                FORBIDDEN,
                now(),
                LocalTime.now()
        );
        return ResponseEntity.status(415).body(errorMessage);
    }
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorMessage> securityError(AuthenticationException exception){
        ErrorMessage errorMessage = new ErrorMessage(
                Map.of("message", exception.getMessage()),
                FORBIDDEN,
                now(),
                LocalTime.now()
        );
        return ResponseEntity.status(415).body(errorMessage);
    }
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ServletException.class)
    public ResponseEntity<ErrorMessage> anotherSecurityError(ServletException exception){
        ErrorMessage errorMessage = new ErrorMessage(
                Map.of("message", exception.getMessage()),
                FORBIDDEN,
                now(),
                LocalTime.now()
        );
        return ResponseEntity.status(415).body(errorMessage);
    }
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorMessage> yetAnotherSecurityError(IOException exception){
        ErrorMessage errorMessage = new ErrorMessage(
                Map.of("message", exception.getMessage()),
                FORBIDDEN,
                now(),
                LocalTime.now()
        );
        return ResponseEntity.status(415).body(errorMessage);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorMessage> yetAnotherSecurityError1(UsernameNotFoundException exception){
        ErrorMessage errorMessage = new ErrorMessage(
                Map.of("message", exception.getMessage()),
                FORBIDDEN,
                now(),
                LocalTime.now()
        );
        return ResponseEntity.status(415).body(errorMessage);
    }

}
