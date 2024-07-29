package com.atmosware.belatrix.managmentService.business.abstracts;

import com.atmosware.belatrix.managmentService.business.dto.dtos.RegisterUserDto;
import com.atmosware.belatrix.managmentService.business.dto.requests.auth.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    String login(LoginRequest loginRequest);
    void register(RegisterUserDto registerUserDto, HttpServletRequest request);
}
