package com.atmosware.belatrix.managmentService.business.concretes;

import com.atmosware.belatrix.core.services.JwtService;
import com.atmosware.belatrix.managmentService.business.dto.dtos.RegisterUserDto;
import com.atmosware.belatrix.managmentService.business.dto.requests.user.CreateAdminRequest;
import com.atmosware.belatrix.managmentService.business.dto.requests.user.UpdateUserRequest;
import com.atmosware.belatrix.managmentService.business.dto.responses.user.GetByIdUserResponse;
import com.atmosware.belatrix.managmentService.business.dto.responses.user.UpdateUserResponse;
import com.atmosware.belatrix.managmentService.business.mappers.UserMapper;
import com.atmosware.belatrix.managmentService.business.rules.UserBusinessRules;
import com.atmosware.belatrix.managmentService.core.exceptions.types.NotFoundException;
import com.atmosware.belatrix.managmentService.dataAccess.UserRepository;
import com.atmosware.belatrix.managmentService.entities.concretes.Organization;
import com.atmosware.belatrix.managmentService.entities.concretes.User;
import com.google.common.net.HttpHeaders;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserManagerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private UserBusinessRules userBusinessRules;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserManager userManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void add_UserWithOrganization_ShouldSaveUser() {
        RegisterUserDto registerUserDto = mock(RegisterUserDto.class);
        Organization organization = mock(Organization.class);
        User user = mock(User.class);

        when(userMapper.toUser(registerUserDto)).thenReturn(user);
        when(passwordEncoder.encode(registerUserDto.password())).thenReturn("encodedPassword");

        userManager.add(registerUserDto, organization);

        verify(userBusinessRules).userCanNotBeDuplicated(registerUserDto.email());
        verify(userMapper).toUser(registerUserDto);
        verify(user).setPassword("encodedPassword");
        verify(user).setOrganization(organization);
        verify(userRepository).save(user);
    }

    @Test
    void add_UserWithOrganizationId_ShouldSaveUser() {
        RegisterUserDto registerUserDto = mock(RegisterUserDto.class);
        UUID organizationId = UUID.randomUUID();
        User user = mock(User.class);

        when(userMapper.toUser(registerUserDto)).thenReturn(user);
        when(passwordEncoder.encode(registerUserDto.password())).thenReturn("encodedPassword");

        userManager.add(registerUserDto, organizationId);

        verify(userBusinessRules).userCanNotBeDuplicated(registerUserDto.email());
        verify(userMapper).toUser(registerUserDto);
        verify(user).setPassword("encodedPassword");
        verify(user).setOrganization(any(Organization.class));
        verify(userRepository).save(user);
    }

    @Test
    void createAdmin_ShouldSaveUser() {
        CreateAdminRequest createAdminRequest = mock(CreateAdminRequest.class);
        User user = mock(User.class);

        when(userMapper.toUser(createAdminRequest)).thenReturn(user);
        when(passwordEncoder.encode(createAdminRequest.password())).thenReturn("encodedPassword");

        userManager.createAdmin(createAdminRequest);

        verify(userBusinessRules).userCanNotBeDuplicated(createAdminRequest.email());
        verify(userMapper).toUser(createAdminRequest);
        verify(user).setPassword("encodedPassword");
        verify(userRepository).save(user);
    }

    @Test
    void getById_ShouldReturnUser() {
        UUID userId = UUID.randomUUID();
        User user = mock(User.class);
        GetByIdUserResponse response = mock(GetByIdUserResponse.class);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toGetByIdUserResponse(user)).thenReturn(response);

        GetByIdUserResponse result = userManager.getById(userId);

        verify(userBusinessRules).userShouldBeExists(userId);
        verify(userRepository).findById(userId);
        verify(userMapper).toGetByIdUserResponse(user);
        assertEquals(response, result);
    }

    @Test
    void loadUserByUsername_ShouldReturnUserDetails() {
        String email = "test@example.com";
        User user = mock(User.class);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        UserDetails result = userManager.loadUserByUsername(email);

        verify(userRepository).findByEmail(email);
        verify(userBusinessRules).userShouldBeExists(Optional.of(user));
        assertEquals(user, result);
    }

    @Test
    void findByEmail_ShouldThrowNotFoundException_WhenUserNotFound() {
        String email = "nonexistent@example.com";
        Optional<User> emptyUserOptional = Optional.empty();

        when(userRepository.findByEmail(email)).thenReturn(emptyUserOptional);
        doThrow(new NotFoundException("User not found")).when(userBusinessRules).userShouldBeExists(emptyUserOptional);

        assertThrows(NotFoundException.class, () -> userManager.findByEmail(email));

        verify(userRepository).findByEmail(email);
        verify(userBusinessRules).userShouldBeExists(emptyUserOptional);
    }
    @Test
    void updateUser_ShouldUpdateUserCorrectly() {
        // Arrange
        String token = "Bearer token";
        String tokenValue = token.substring(7); // "Bearer " kısmını çıkararak sadece token'ı alın
        UUID userId = UUID.randomUUID();
        String encodedPassword = "encodedPassword";
        User user = new User();
        UpdateUserRequest updateUserRequest = new UpdateUserRequest("newPassword");

        Claims claims = mock(Claims.class);
        when(claims.get("userId").toString()).thenReturn(userId.toString()); // userId'yi mock'luyoruz
        when(httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(token);
        when(jwtService.getClaims(tokenValue)).thenReturn(claims);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(updateUserRequest.password())).thenReturn(encodedPassword);
        when(userRepository.save(user)).thenReturn(user);
        UpdateUserResponse expectedResponse = new UpdateUserResponse(user.getEmail(), userId, LocalDateTime.now(), LocalDateTime.now(), "Test organization");
        when(userMapper.toUpdateUserResponse(user)).thenReturn(expectedResponse);

        // Act
        UpdateUserResponse actualResponse = userManager.updateUser(updateUserRequest, httpServletRequest);

        // Assert
        verify(httpServletRequest).getHeader(HttpHeaders.AUTHORIZATION);
        verify(jwtService).getClaims(tokenValue);
        verify(claims).get("userId"); // claims nesnesinin get metodunu kontrol edin
        verify(userRepository).findById(userId);
        verify(passwordEncoder).encode(updateUserRequest.password());
        verify(userRepository).save(user);
        verify(userMapper).toUpdateUserResponse(user);

        assertEquals(expectedResponse, actualResponse);
    }



    @Test
    void delete_ShouldMarkUsersAsDeleted() {
        List<User> users = List.of(mock(User.class), mock(User.class));

        userManager.delete(users);

        users.forEach(user -> verify(user).setDeletedDate(any(LocalDateTime.class)));
        verify(userRepository).saveAll(users);
    }
}
