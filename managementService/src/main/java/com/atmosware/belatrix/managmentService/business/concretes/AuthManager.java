package com.atmosware.belatrix.managmentService.business.concretes;

import com.atmosware.belatrix.managmentService.business.abstracts.AuthService;
import com.atmosware.belatrix.managmentService.business.abstracts.UserService;
import com.atmosware.belatrix.managmentService.business.dto.requests.LoginRequest;
import com.atmosware.belatrix.managmentService.business.rules.AuthBusinessRules;
import com.atmosware.belatrix.managmentService.core.service.JwtService;
import com.atmosware.belatrix.managmentService.entities.concretes.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class AuthManager implements AuthService {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthBusinessRules authBusinessRules;

    @Override// TODO:Login Response döndür
    public String login(LoginRequest loginRequest) {
        this.authBusinessRules.emailAndPasswordShouldBeMatch(loginRequest.email(),loginRequest.password());

        User user = this.userService.findByEmail(loginRequest.email());

        return generateJwt(user);

    }

    private String generateJwt(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("username", user.getEmail());
        claims.put("organizationId",user.getOrganization().getId());
        List<String> authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        claims.put("roles", authorities);
        return this.jwtService.generateToken(user.getEmail(), claims);
    }
}
