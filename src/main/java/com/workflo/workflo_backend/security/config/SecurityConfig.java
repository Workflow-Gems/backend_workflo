package com.workflo.workflo_backend.security.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.workflo.workflo_backend.security.filter.WorkFloAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
    private final JWTService jwtService;
    private final ObjectMapper mapper;
    private final AuthenticationManager manager;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        WorkFloAuthenticationFilter filter = new WorkFloAuthenticationFilter(jwtService, mapper, manager);
        filter.setFilterProcessesUrl("/login");
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(s-> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterAt(filter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(in -> in.requestMatchers(HttpMethod.POST, "/login", "/api/v1/register").permitAll()
                                               .requestMatchers(HttpMethod.GET, "/api/v1/user/confirm").permitAll()
                                               .anyRequest().authenticated()).build();
//        return null;
    }
}
