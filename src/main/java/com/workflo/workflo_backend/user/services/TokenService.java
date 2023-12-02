package com.workflo.workflo_backend.user.services;

import com.workflo.workflo_backend.exceptions.TokenExceptions;
import com.workflo.workflo_backend.user.models.User;

public interface TokenService {
    String generateToken(User user);

    String confirmToken(String email, String token) throws TokenExceptions;
}
