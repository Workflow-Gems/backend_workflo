package com.workflo.workflo_backend.exceptions;

public class UpdateNotAllowedException extends WorkFloException{
    public UpdateNotAllowedException(String message) {
        super(message);
    }
}
