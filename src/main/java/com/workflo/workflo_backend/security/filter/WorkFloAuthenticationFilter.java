package com.workflo.workflo_backend.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workflo.workflo_backend.exceptions.UserNotVerifiedException;
import com.workflo.workflo_backend.security.config.JWTService;
import com.workflo.workflo_backend.user.dtos.request.LoginRequest;
import com.workflo.workflo_backend.user.dtos.response.LoginResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.InputStream;


@AllArgsConstructor
public class WorkFloAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JWTService jwtService;
    private final ObjectMapper mapper;
    private final AuthenticationManager authenticationManager;
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try(InputStream stream = request.getInputStream()){
            LoginRequest loginRequest = mapper.readValue(stream, LoginRequest.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                                                                                    loginRequest.getPassword());

            Authentication authUser = authenticationManager.authenticate(authentication);
            if (authUser.isAuthenticated()){
                SecurityContext context = SecurityContextHolder.getContext();
                context.setAuthentication(authUser);
                return authUser;
            }
        } catch (IOException exception){
            throw new BadCredentialsException("failed to verify user with provided credentials");
        }
        throw new RuntimeException("couldn't authenticate user");
    }
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain, Authentication authResult)
                                            throws IOException, ServletException {
        String token = jwtService.generateToken(authResult.getPrincipal().toString());
        LoginResponse loginResponse = new LoginResponse(token);
        mapper.writeValue(response.getOutputStream(), loginResponse);
    }
}
