package com.atmosware.belatrix.managmentService.business.security;

import com.atmosware.belatrix.managmentService.core.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Service;

@Service
public class SecurityManager implements SecurityService {
    private static final String[] WHITE_LIST_URLS = {
            "/swagger-ui/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "management-service/api/v1/auth/**",
            "/actuator/prometheus",
    };
    @Override
    //TODO: roleleri constanta çek
    public HttpSecurity configureSecurity(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(x-> x
                .requestMatchers(WHITE_LIST_URLS).permitAll()
                .requestMatchers(HttpMethod.GET,"management-service/api/v1/organizations").hasAnyAuthority("admin")
                .requestMatchers(HttpMethod.PUT,"management-service/api/v1/organizations/organization").hasAnyAuthority("organization")
                .requestMatchers(HttpMethod.PUT,"management-service/api/v1/organizations/admin/{id}").hasAnyAuthority("admin")
                .requestMatchers(HttpMethod.DELETE, "management-service/api/v1/organizations/{id}").hasAnyAuthority("admin")
                .requestMatchers(HttpMethod.POST, "management-service/api/v1/organizations").hasAnyAuthority("admin")
                .requestMatchers(HttpMethod.POST, "management-service/api/v1/roles").hasAnyAuthority("admin")
                .requestMatchers("management-service/api/v1/user-role").hasAnyAuthority("admin")
                .requestMatchers(HttpMethod.POST, "management-service/api/v1/auth/register").hasAnyAuthority("organization")
                .requestMatchers(HttpMethod.POST,"management-service/api/v1/invitations").hasAnyAuthority("organization")
                .anyRequest().authenticated()
        );
        return http;
    }
}
