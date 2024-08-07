package com.atmosware.belatrix.examSercvice.business.configurations;

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
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        this.baseSecurityService.securityFilterChain(http);
        http.authorizeHttpRequests(x->x
                .requestMatchers(WHITE_LIST_URLS).permitAll()
                .requestMatchers(HttpMethod.PUT,"exam-service/api/v1/rules/{id}").hasAnyAuthority("admin")
                .requestMatchers(HttpMethod.DELETE,"exam-service/api/v1/rules/{id}").hasAnyAuthority("admin")
                .requestMatchers(HttpMethod.POST,"exam-service/api/v1/rules").hasAnyAuthority("admin")
                .requestMatchers(HttpMethod.PUT,"exam-service/api/v1/tests/organization","exam-service/api/v1/tests/organization/{id}","exam-service/api/v1/tests/organization/extend-end-date/{id}","exam-service/api/v1/tests/organization/add-test-rule").hasAnyAuthority("organization")
                .requestMatchers(HttpMethod.PUT,"exam-service/api/v1/tests","exam-service/api/v1/tests/{id}","exam-service/api/v1/tests/extend-end-date/{id}","exam-service/api/v1/tests/add-test-rule").hasAnyAuthority("admin")
                .requestMatchers(HttpMethod.GET,"exam-service/api/v1/tests/organization/{id}","exam-service/api/v1/tests/organization","exam-service/api/v1/tests/organization/get-all-test-rule-for-test/{id}").hasAnyAuthority("organization")
                .requestMatchers(HttpMethod.GET,"exam-service/api/v1/tests/{id}","exam-service/api/v1/tests","exam-service/api/v1/tests/get-all-test-rule-for-test/{id}").hasAnyAuthority("admin")
                .requestMatchers(HttpMethod.DELETE,"exam-service/api/v1/tests/organization/{id}","exam-service/api/v1/tests/organization","exam-service/api/v1/tests/organization/remove-test-rule").hasAnyAuthority("organization")
                .requestMatchers(HttpMethod.DELETE,"exam-service/api/v1/tests","exam-service/api/v1/tests/{id}","exam-service/api/v1/tests/remove-test-rule").hasAnyAuthority("admin")
                .anyRequest().authenticated()
        );
        return http.build();
    }
}
