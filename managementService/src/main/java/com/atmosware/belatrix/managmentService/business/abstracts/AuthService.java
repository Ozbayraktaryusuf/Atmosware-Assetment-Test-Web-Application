package com.atmosware.belatrix.managmentService.business.abstracts;

import com.atmosware.belatrix.managmentService.business.dto.requests.auth.LoginRequest;

public interface AuthService {
    String login(LoginRequest loginRequest);
}
