package com.workflo.workflo_backend.security.filter;

import com.workflo.workflo_backend.security.config.JWTService;
import com.workflo.workflo_backend.user.models.User;
import com.workflo.workflo_backend.user.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;



@AllArgsConstructor
public class WorkFloAuthorizationFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final JWTService jwtService;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        boolean confirmPath = request.getMethod().equals("POST") &&
                (Arrays.stream(getEndpoints()).toList()).contains(request.getServletPath());
        if (confirmPath) filterChain.doFilter(request, response);

        else {
            String authHeader = request.getHeader("Authorization");
            String authBearer = authHeader.substring("Bearer ".length());

            String email = jwtService.extractEmailFromTheHeader(authBearer);

            User user = userService.getUserWithMail(email);

            Collection<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getUserStatus().name()));

            Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);

            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(authentication);
            filterChain.doFilter(request, response);

        }

    }


    public String[] getEndpoints(){
        return new String[]{"/login", "/api/v1/register"};
    }
}
