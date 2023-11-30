package com.workflo.workflo_backend.exceptions;

public class UserNotFoundException extends WorkFloException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
