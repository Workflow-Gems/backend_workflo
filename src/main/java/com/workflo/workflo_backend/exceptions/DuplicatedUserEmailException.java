package com.workflo.workflo_backend.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class DuplicatedUserEmailException extends DataIntegrityViolationException {
    public DuplicatedUserEmailException(String message) {
        super(message);
    }
}
