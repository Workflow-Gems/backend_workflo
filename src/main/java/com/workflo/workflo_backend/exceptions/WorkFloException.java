package com.workflo.workflo_backend.exceptions;

public class WorkFloException extends Throwable{

    public WorkFloException(String message) {
        super(message);
    }
    public WorkFloException(String message, Throwable cause) {
        super(message, cause);
    }
}
