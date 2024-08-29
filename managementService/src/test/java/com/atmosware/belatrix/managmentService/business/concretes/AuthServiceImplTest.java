package com.atmosware.belatrix.managmentService.business.concretes;

import com.atmosware.belatrix.core.services.JwtService;
import com.atmosware.belatrix.managmentService.business.abstracts.UserService;
import com.atmosware.belatrix.managmentService.business.dto.dtos.RegisterUserDto;
import com.atmosware.belatrix.managmentService.business.dto.requests.auth.LoginRequest;
import com.atmosware.belatrix.managmentService.business.mappers.UserMapper;
import com.atmosware.belatrix.managmentService.business.mappers.UserMapperImpl;
import com.atmosware.belatrix.managmentService.business.rules.AuthBusinessRules;
import com.atmosware.belatrix.managmentService.business.rules.UserBusinessRules;
import com.atmosware.belatrix.managmentService.core.business.abstracts.MessageService;
import com.atmosware.belatrix.managmentService.dataAccess.UserRepository;
import com.atmosware.belatrix.managmentService.entities.concretes.Organization;
import com.atmosware.belatrix.managmentService.entities.concretes.Roles;
import com.atmosware.belatrix.managmentService.entities.concretes.User;
import com.atmosware.belatrix.managmentService.entities.concretes.UserRole;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {
    private final String secretKey = "C53m0R5fXD2VXQwoWbV6Rhqk44l/fY0n6rk67028lFw9ZCU9nIpc0V6N1S7hNGHSj6nNGKszIHMUCGxOwatyQXtcA+HmkiCGtO19bhhVEEhfDVxdYP/PLDTnolseuAMP9bYYmXPUXZ/79iRq90kIsM37Uiw/Q3xFFjHObjzzD78ZP8ucmaavcEBTeW9dpOieMvyS7Zdz4ut/slMtIpq1gKBKBw+r3e3sUbUGmoINGJoBsPEQflbE10J1aTRLLREq1EmUNw9VZmVOtCrVErwKawkQFVniWieqAeQze+OY1BfkjYI11lhO7Y7LyOl3Vx35EezWhrYj8ceajsnEr7CIMg==";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private UserService userService;
    private JwtService jwtService;
    private AuthBusinessRules authBusinessRules;
    private UserRepository userRepository;
    private MessageService messageService;
    private AuthServiceImpl authService;
    private AuthenticationManager authenticationManager;

    @BeforeEach
    void setUp() {
        jwtService = mock(JwtService.class);
        userRepository = mock(UserRepository.class);
        messageService = mock(MessageService.class);
        authenticationManager = mock(AuthenticationManager.class);

        UserMapper userMapper = new UserMapperImpl();

        UserBusinessRules userBusinessRules = new UserBusinessRules(userRepository, messageService);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        userService = new UserServiceImpl(userRepository, userMapper, passwordEncoder, userBusinessRules, jwtService);
        authBusinessRules = new AuthBusinessRules(userRepository, authenticationManager, messageService);
        authService = new AuthServiceImpl(userService, jwtService, authBusinessRules);
    }

    @Test
    void login_ShouldReturnJwtToken() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("user@example.com", "password");
        User user = new User();
        Organization organization = new Organization(UUID.randomUUID());
        user.setOrganization(organization);
        user.setEmail("user@example.com");
        user.setId(UUID.randomUUID());

        // Create a valid GrantedAuthority mock
        Roles role = new Roles();
        role.setName("organization");

        UserRole userRole = new UserRole(user, role);

        // Add authorities to the user
        user.setAuthorities(List.of(userRole));


        //admin token
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJhZG1pbiJdLCJpZCI6IjhlMmYyNzQwLTJiMmUtNGM4My05YTFkLTRjMTkzZmJlOWI3MSIsInN1YiI6ImFkbWluQGFkbWluLmNvbSIsImlhdCI6MTcyMzcyNjczNiwiZXhwIjoxNzIzNzI3MzM2fQ.UJir9U_m7p4NdXcZHqyWOeLAJnbviihpTRCUSgqPDfI";

        // Mock Authentication object
        Authentication authentication = mock(Authentication.class);
        //when(authentication.getPrincipal()).thenReturn(user);
        when(authentication.isAuthenticated()).thenReturn(true);

        // Mock authenticationManager.authenticate to return our mock Authentication
        when(authenticationManager.authenticate(any())).thenReturn(authentication);

        // Mock Claims
//        Claims mockClaims = mock(Claims.class);
//        when(mockClaims.get("id")).thenReturn(user.getId());
//        when(mockClaims.get("roles")).thenReturn(List.of("user"));
//        when(jwtService.getClaims(anyString())).thenReturn(mockClaims);
        when(jwtService.generateToken(eq(user.getEmail()), any(Map.class))).thenReturn(token);

        // Mock userRepository.findByEmail
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        // Act
        String actualToken = authService.login(loginRequest);

        // Assert
        assertEquals(token, actualToken);
    }


    @Test
    void register_ShouldRegisterUserCorrectly() throws JsonProcessingException {
        // Arrange
        RegisterUserDto registerUserDto = new RegisterUserDto("user@example.com", "password");
        UUID organizationId = UUID.randomUUID(); // Geçerli UUID oluştur
        HttpServletRequest request = mock(HttpServletRequest.class);

        // Organization token
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJvcmdhbml6YXRpb25JZCI6ImY5YzZkODM0LWJlYjktNDY5Yy05MjdjLTIyMzQ0MDM2MzNhYSIsInJvbGVzIjpbIm9yZ2FuaXphdGlvbiJdLCJpZCI6ImRlYzg1MzRhLTBkZmQtNDIwMS1iMGFjLTY2YWY1OWUzNjU5MCIsInN1YiI6InN0cmluZ0BzdHJpbmcuY29tIiwiaWF0IjoxNzI0ODU3NDMzLCJleHAiOjE3MjQ4NTgwMzN9.eQyJVW17dfPhun7CR-eZKQzSktrSGWs7bYC2XXfjqnU";
        String encodedJwt = token.split(" ")[1];
        String[] tokenParts = encodedJwt.split("\\.");
        String payloadPart = tokenParts[1];
        Base64.Decoder decoder = Base64.getDecoder();
        String payload = new String(decoder.decode(payloadPart));
        HashMap hashMap = objectMapper.readValue(payload, HashMap.class);
        Claims claims = new DefaultClaims(hashMap);
        //Claims claims = Jwts.parser().verifyWith(new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName())).build().parseSignedClaims(token).getPayload();
        //String token = Jwts.builder().setSubject(organizationId.toString()).claim("organizationId", organizationId).signWith(new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName())).compact();

        // Mock HttpServletRequest
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(token);

        // Mock JwtService
        when(jwtService.getClaims(encodedJwt)).thenReturn(claims);

        // Act
        authService.register(registerUserDto, request);

        // Assert
        verify(request).getHeader(HttpHeaders.AUTHORIZATION);
        verify(jwtService).getClaims(encodedJwt);
    }


}
