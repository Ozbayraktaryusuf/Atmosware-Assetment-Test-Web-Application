package com.atmosware.belatrix.managmentService.business.concretes;

import com.atmosware.belatrix.core.services.JwtService;
import com.atmosware.belatrix.managmentService.business.abstracts.UserService;
import com.atmosware.belatrix.managmentService.business.dto.dtos.RegisterUserDto;
import com.atmosware.belatrix.managmentService.business.dto.requests.auth.LoginRequest;
import com.atmosware.belatrix.managmentService.business.rules.AuthBusinessRules;
import com.atmosware.belatrix.managmentService.entities.concretes.Roles;
import com.atmosware.belatrix.managmentService.entities.concretes.User;
import com.atmosware.belatrix.managmentService.entities.concretes.UserRole;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {
    private UserService userService;
    private JwtService jwtService;
    private AuthBusinessRules authBusinessRules;

    @BeforeEach
    void setUp() {

    }

    @Test
    void login_ShouldReturnJwtToken() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("user@example.com", "password");
        User user = new User();
        user.setEmail("user@example.com");
        user.setId(UUID.randomUUID());

        // Create a valid GrantedAuthority mock
        GrantedAuthority authority = mock(GrantedAuthority.class);
        when(authority.getAuthority()).thenReturn("user");

        UserRole userRole =  new UserRole(user,new Roles());

        // Add authorities to the user
        user.setAuthorities(List.of(userRole)); // Add non-null authority

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("roles", List.of("user"));

        String token = "jwtToken";

        when(userService.findByEmail(loginRequest.email())).thenReturn(user);
        when(userRole.getRole().getAuthority()).thenReturn("organizationId");

        // Mock Claims
        Claims mockClaims = mock(Claims.class);
        when(mockClaims.get("id")).thenReturn(user.getId());
        when(mockClaims.get("roles")).thenReturn(List.of("user"));
        when(jwtService.getClaims(anyString())).thenReturn(mockClaims);
        when(jwtService.generateToken(eq(user.getEmail()), any(Map.class))).thenReturn(token);

        // Act
        String actualToken = authManager.login(loginRequest);

        // Assert
        verify(authBusinessRules).emailAndPasswordShouldBeMatch(loginRequest.email(), loginRequest.password());
        verify(userService).findByEmail(loginRequest.email());
        verify(jwtService).generateToken(user.getEmail(), claims);

        assertEquals(token, actualToken);
    }

    @Test
    void register_ShouldRegisterUserCorrectly() {
        // Arrange
        RegisterUserDto registerUserDto = new RegisterUserDto("user@example.com", "password");
        UUID organizationId = UUID.randomUUID();
        HttpServletRequest request = mock(HttpServletRequest.class);

        // Mock Claims
        Claims mockClaims = mock(Claims.class);
        when(mockClaims.get("organizationId")).thenReturn(organizationId.toString());

        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer token");
        when(jwtService.getClaims("token")).thenReturn(mockClaims);

        // Act
        authManager.register(registerUserDto, request);

        // Assert
        verify(request).getHeader(HttpHeaders.AUTHORIZATION);
        verify(jwtService).getClaims("token");
        verify(authBusinessRules).userShouldHaveAnOrganization(organizationId);
        verify(userService).add(registerUserDto, organizationId);
    }
}
