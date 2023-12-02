package com.workflo.workflo_backend.user.services.impl;

import com.workflo.workflo_backend.exceptions.TokenExceptions;
import com.workflo.workflo_backend.user.models.Token;
import com.workflo.workflo_backend.user.models.User;
import com.workflo.workflo_backend.user.repository.TokenRepository;
import com.workflo.workflo_backend.user.services.TokenService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

import static java.time.LocalTime.now;
@Slf4j
@Service
@Lazy
@AllArgsConstructor
public class WorkFloTokenService implements TokenService {
    private final TokenRepository tokenRepository;
    @Override
    @Transactional
    public String generateToken(User user) {
        Token token = new Token();
        String generatedToken = generateToken();
        Optional<Token> foundToken = tokenRepository.findTokenByToken(generatedToken);
        if (foundToken.isPresent()) generatedToken = generateToken();
        token.setToken(generatedToken);
        token.setUser(user);
        token.setTimeCreated(now());
        token.setExpiryTime(now().plusMinutes(30));
        Token savedToken = tokenRepository.save(token);
        return savedToken.getToken();
    }
    private String generateToken(){
        return UUID.randomUUID().toString();
    }
    @Override
    @Transactional
    public String confirmToken(String email, String token) throws TokenExceptions {
        Token foundToken = tokenRepository.findTokenByToken(token)
                .orElseThrow(()-> new TokenExceptions("token not found..."));
        LocalTime expiryTime = foundToken.getExpiryTime();
        if (foundToken.getConfirmAt() != null){
            throw new TokenExceptions("Email already confirmed...");
        }
        if (expiryTime.isBefore(now())){
            throw new TokenExceptions("Email verification failed... time elapsed...");
        }
        if (foundToken.getUser().getEmail().equals(email)){
            foundToken.setConfirmAt(now());
            tokenRepository.save(foundToken);
            return "email confirmed successfully...";
        }
        throw new TokenExceptions("Email already confirmed...");
    }
}