package com.atmosware.belatrix.questionService.business.configurations;

import com.atmosware.belatrix.core.configurations.BaseSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final BaseSecurityService baseSecurityService;
    private static final String[] WHITE_LIST_URLS = {
            "/swagger-ui/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/actuator/prometheus",
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        this.baseSecurityService.securityFilterChain(http);
        http.authorizeHttpRequests(x->x
                .requestMatchers(WHITE_LIST_URLS).permitAll()
                .requestMatchers(HttpMethod.PUT,"question-service/api/v1/questions/{id}","question-service/api/v1/questions/add-option/{id}").hasAnyAuthority("admin")
                .requestMatchers(HttpMethod.DELETE,"question-service/api/v1/questions/{id}","question-service/api/v1/questions/delete-option/{id}").hasAnyAuthority("admin")
                .requestMatchers(HttpMethod.GET,"question-service/api/v1/questions/organization").hasAnyAuthority("organization")
                .requestMatchers(HttpMethod.GET,"question-service/api/v1/questions","question-service/api/v1/questions/{id}").hasAnyAuthority("admin")
                .anyRequest().authenticated()
        );
        return http.build();
    }
}
