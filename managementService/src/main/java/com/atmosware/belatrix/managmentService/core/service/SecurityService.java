package com.atmosware.belatrix.managmentService.core.service;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public interface SecurityService {
    HttpSecurity configureSecurity(HttpSecurity http) throws Exception;
}
