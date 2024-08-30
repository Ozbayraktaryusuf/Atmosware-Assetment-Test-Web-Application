package com.atmosware.belatrix.managmentService.business.concretes;

import com.atmosware.belatrix.core.services.JwtService;
import com.atmosware.belatrix.managmentService.business.dto.dtos.RegisterUserDto;
import com.atmosware.belatrix.managmentService.business.dto.requests.user.CreateAdminRequest;
import com.atmosware.belatrix.managmentService.business.dto.requests.user.UpdateUserRequest;
import com.atmosware.belatrix.managmentService.business.dto.responses.user.GetByIdUserResponse;
import com.atmosware.belatrix.managmentService.business.dto.responses.user.UpdateUserResponse;
import com.atmosware.belatrix.managmentService.business.mappers.UserMapper;
import com.atmosware.belatrix.managmentService.business.mappers.UserMapperImpl;
import com.atmosware.belatrix.managmentService.business.rules.UserBusinessRules;
import com.atmosware.belatrix.managmentService.core.business.abstracts.MessageService;
import com.atmosware.belatrix.managmentService.core.exceptions.types.NotFoundException;
import com.atmosware.belatrix.managmentService.dataAccess.UserRepository;
import com.atmosware.belatrix.managmentService.entities.concretes.Organization;
import com.atmosware.belatrix.managmentService.entities.concretes.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private MessageService messageService;
    private PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private HttpServletRequest httpServletRequest;
    private UserBusinessRules userBusinessRules;
    private JwtService jwtService;
    private UserServiceImpl userServiceImpl;

    @BeforeEach
    void setUp() {
        jwtService = mock(JwtService.class);
        userRepository = mock(UserRepository.class);
        messageService = mock(MessageService.class);
        passwordEncoder = mock(PasswordEncoder.class);
        httpServletRequest = mock(HttpServletRequest.class);

        userMapper = new UserMapperImpl();

        userBusinessRules = new UserBusinessRules(userRepository, messageService);
        userServiceImpl = new UserServiceImpl(userRepository, userMapper, passwordEncoder, userBusinessRules, jwtService);
    }

    @Test
    void add_UserWithOrganization_ShouldSaveUser() {
        RegisterUserDto registerUserDto = new RegisterUserDto("test@test.com","test123");
        Organization organization = new Organization(UUID.randomUUID());
        User user = userMapper.toUser(registerUserDto);

        when(passwordEncoder.encode(registerUserDto.password())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        userServiceImpl.add(registerUserDto, organization);

        assertEquals(registerUserDto.email(),user.getEmail());
    }

    @Test
    void add_UserWithOrganizationId_ShouldSaveUser() {
        RegisterUserDto registerUserDto = new RegisterUserDto("test@test.com","test123");
        UUID organizationId = UUID.randomUUID();
        User user = userMapper.toUser(registerUserDto);

        when(passwordEncoder.encode(registerUserDto.password())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        userServiceImpl.add(registerUserDto, organizationId);

        assertEquals(registerUserDto.email(),user.getEmail());
    }

    @Test
    void createAdmin_ShouldSaveUser() {
        CreateAdminRequest createAdminRequest = new CreateAdminRequest("test@test.com","test123");
        User user = userMapper.toUser(createAdminRequest);

        when(passwordEncoder.encode(createAdminRequest.password())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        userServiceImpl.createAdmin(createAdminRequest);

        assertEquals(createAdminRequest.password(),user.getPassword());
        assertEquals(createAdminRequest.email(),user.getEmail());
    }

    @Test
    void getById_ShouldReturnUser() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        GetByIdUserResponse response = userMapper.toGetByIdUserResponse(user);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        GetByIdUserResponse result = userServiceImpl.getById(userId);

        assertEquals(response, result);
    }

    @Test
    void loadUserByUsername_ShouldReturnUserDetails() {
        String email = "test@example.com";
        User user = new User();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        UserDetails result = userServiceImpl.loadUserByUsername(email);

        verify(userRepository).findByEmail(email);
        assertEquals(user, result);
    }

    @Test
    void findByEmail_ShouldThrowNotFoundException_WhenUserNotFound() {
        String email = "nonexistent@example.com";
        Optional<User> emptyUserOptional = Optional.empty();

        when(userRepository.findByEmail(email)).thenReturn(emptyUserOptional);

        assertThrows(NotFoundException.class, () -> userServiceImpl.findByEmail(email));

        verify(userRepository).findByEmail(email);
    }
    @Test
    void updateUser_ShouldUpdateUserCorrectly() throws JsonProcessingException {
        // Arrange
        UUID userId = UUID.randomUUID();
        String encodedPassword = "encodedPassword";
        User user = new User();
        user.setId(userId);
        UpdateUserRequest updateUserRequest = new UpdateUserRequest("newPassword");

        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJvcmdhbml6YXRpb25JZCI6ImY5YzZkODM0LWJlYjktNDY5Yy05MjdjLTIyMzQ0MDM2MzNhYSIsInJvbGVzIjpbIm9yZ2FuaXphdGlvbiJdLCJpZCI6ImRlYzg1MzRhLTBkZmQtNDIwMS1iMGFjLTY2YWY1OWUzNjU5MCIsInN1YiI6InN0cmluZ0BzdHJpbmcuY29tIiwiaWF0IjoxNzI0ODU3NDMzLCJleHAiOjE3MjQ4NTgwMzN9.eQyJVW17dfPhun7CR-eZKQzSktrSGWs7bYC2XXfjqnU";
        String encodedJwt = token.split(" ")[1];
        String[] tokenParts = encodedJwt.split("\\.");
        String payloadPart = tokenParts[1];
        Base64.Decoder decoder = Base64.getDecoder();
        String payload = new String(decoder.decode(payloadPart));
        HashMap hashMap = objectMapper.readValue(payload, HashMap.class);
        Claims claims = new DefaultClaims(hashMap);


        when(httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(token);

        when(jwtService.getClaims(encodedJwt)).thenReturn(claims);

        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(updateUserRequest.password())).thenReturn(encodedPassword);
        when(userRepository.save(user)).thenReturn(user);
        UpdateUserResponse expectedResponse = new UpdateUserResponse(user.getEmail(), userId, LocalDateTime.now(), LocalDateTime.now(), "Test organization");

        // Act
        UpdateUserResponse actualResponse = userServiceImpl.updateUser(updateUserRequest, httpServletRequest);

        // Assert
        verify(httpServletRequest).getHeader(HttpHeaders.AUTHORIZATION);
        verify(jwtService).getClaims(encodedJwt);
        verify(userRepository).findById(any());
        verify(passwordEncoder).encode(updateUserRequest.password());
        verify(userRepository).save(user);

        assertEquals(expectedResponse.id(), actualResponse.id());
    }



    @Test
    void delete_ShouldMarkUsersAsDeleted() {
        List<User> users = List.of(new User(), new User());

        userServiceImpl.delete(users);

        verify(userRepository).saveAll(users);
    }
}
