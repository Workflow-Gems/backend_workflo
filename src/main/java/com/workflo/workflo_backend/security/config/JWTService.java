package com.workflo.workflo_backend.security.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;


@Component
public class JWTService {

    public String generateToken(String email){
        return  JWT.create()
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plus(1440, ChronoUnit.MINUTES))
                .withIssuer("WorkFlo Inc.")
                .withSubject(email)
                .sign(Algorithm.HMAC512("secret"));
    }
    public String extractEmailFromTheHeader(String token){
        JWTVerifier verifier = JWT.require(Algorithm.HMAC512("secret"))
                                  .withIssuer("WorkFlo Inc.")
                                  .build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getSubject();
    }
}
