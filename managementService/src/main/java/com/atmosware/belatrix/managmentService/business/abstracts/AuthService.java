package com.atmosware.belatrix.managmentService.business.abstracts;

import com.atmosware.belatrix.managmentService.business.dto.requests.LoginRequest;
import com.atmosware.belatrix.managmentService.business.dto.responses.LoginResponse;

public interface AuthService {
    String login(LoginRequest loginRequest);
}
