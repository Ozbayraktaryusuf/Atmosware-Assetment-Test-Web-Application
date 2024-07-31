package com.atmosware.belatrix.managmentService.business.concretes;

import com.atmosware.belatrix.core.services.JwtService;
import com.atmosware.belatrix.managmentService.business.abstracts.AuthService;
import com.atmosware.belatrix.managmentService.business.abstracts.UserService;
import com.atmosware.belatrix.managmentService.business.dto.dtos.RegisterUserDto;
import com.atmosware.belatrix.managmentService.business.dto.requests.auth.LoginRequest;
import com.atmosware.belatrix.managmentService.business.rules.AuthBusinessRules;
import com.atmosware.belatrix.managmentService.entities.concretes.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthManager implements AuthService {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthBusinessRules authBusinessRules;

    @Override// TODO:Login Response döndür
    public String login(LoginRequest loginRequest) {
        this.authBusinessRules.emailAndPasswordShouldBeMatch(loginRequest.email(), loginRequest.password());

        User user = this.userService.findByEmail(loginRequest.email());

        return generateJwt(user);

    }

    @Override
    @Transactional
    public void register(RegisterUserDto registerUserDto, HttpServletRequest request) {
        UUID organizationId = UUID.fromString(this.jwtService.
                getClaims(
                        request
                                .getHeader
                                        (HttpHeaders.AUTHORIZATION).substring(7))
                .get("organizationId")
                .toString());
        this.authBusinessRules.userShouldHaveAnOrganization(organizationId);

        this.userService.add(registerUserDto,organizationId);
    }

    private String generateJwt(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        if (!((user.getAuthorities().stream().map(x->x.getAuthority().equals("admin")).toList().get(0)))) {
            claims.put("organizationId", user.getOrganization().getId());
        }
        List<String> authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        claims.put("roles", authorities);
        return this.jwtService.generateToken(user.getEmail(), claims);
    }
}
