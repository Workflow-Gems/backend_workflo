package com.workflo.workflo_backend.services;


import com.workflo.workflo_backend.exceptions.TokenExceptions;
import com.workflo.workflo_backend.user.models.User;
import com.workflo.workflo_backend.user.services.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest
public class ConfirmationTokenTest {

    @Autowired
    private TokenService tokenService;
    @Test
    public void generateTokenTest(){
        String token = tokenService.generateToken(new User());
        assertThat(token).isNotNull();
    }
    @Test
    public void confirmToken() throws TokenExceptions {
        String email = "pijog53310@frandin.com";
        String token = "b5fd4f2e-2060-4e71-8e41-ef970b2c5c28";
        String result = tokenService.confirmToken(email, token);
        log.info("{}", result);
        assertThat(result).isNotNull();
        assertThat(result).contains("token successfully confirmed...");
    }
    @Test
    public void confirmedTokenThrowsErrorWhenTriedAgain() throws TokenExceptions {
        String email = "pijog53310@frandin.com";
        String token = "b5fd4f2e-2060-4e71-8e41-ef970b2c5c28";


        assertThrows(TokenExceptions.class, ()-> tokenService.confirmToken(email, token));

        try{
            tokenService.confirmToken(email, token);
        }catch (TokenExceptions exception){
            assertEquals("Email already confirmed...", exception.getMessage());
        }
    }

}
