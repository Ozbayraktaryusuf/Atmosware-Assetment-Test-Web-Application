package com.atmosware.belatrix.managmentService.business.concretes;

import com.atmosware.belatrix.core.services.JwtService;
import com.atmosware.belatrix.managmentService.business.abstracts.RoleService;
import com.atmosware.belatrix.managmentService.business.abstracts.UserService;
import com.atmosware.belatrix.managmentService.business.dto.requests.userRole.CreateUserRoleRequest;
import com.atmosware.belatrix.managmentService.business.dto.requests.userRole.UpdateUserRoleRequest;
import com.atmosware.belatrix.managmentService.business.dto.responses.role.GetByIdRoleResponse;
import com.atmosware.belatrix.managmentService.business.dto.responses.user.GetByIdUserResponse;
import com.atmosware.belatrix.managmentService.business.dto.responses.userRole.CreateUserRoleResponse;
import com.atmosware.belatrix.managmentService.business.dto.responses.userRole.UpdateUserRoleResponse;
import com.atmosware.belatrix.managmentService.business.mappers.*;
import com.atmosware.belatrix.managmentService.business.rules.RoleBusinessRules;
import com.atmosware.belatrix.managmentService.business.rules.UserBusinessRules;
import com.atmosware.belatrix.managmentService.business.rules.UserRoleBusinessRules;
import com.atmosware.belatrix.managmentService.core.business.abstracts.MessageService;
import com.atmosware.belatrix.managmentService.dataAccess.RoleRepository;
import com.atmosware.belatrix.managmentService.dataAccess.UserRepository;
import com.atmosware.belatrix.managmentService.dataAccess.UserRoleRepository;
import com.atmosware.belatrix.managmentService.entities.concretes.Roles;
import com.atmosware.belatrix.managmentService.entities.concretes.User;
import com.atmosware.belatrix.managmentService.entities.concretes.UserRole;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class UserRoleServiceImplTest {
    private UserRoleServiceImpl userRoleServiceImpl;
    private UserRoleRepository userRoleRepository;
    private UserRoleMapper userRoleMapper;
    private UserRoleBusinessRules userRoleBusinessRules;
    private MessageService messageService;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;
    private UserBusinessRules userBusinessRules;
    private UserRepository userRepository;
    private RoleMapper roleMapper;
    private RoleBusinessRules roleBusinessRules;
    private RoleRepository roleRepository;
    private UserService userService;
    private RoleService roleService;
    public UserRoleServiceImplTest() {

        JwtService jwtService = mock(JwtService.class);
        userRepository = mock(UserRepository.class);
        messageService = mock(MessageService.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userMapper = new UserMapperImpl();

        userBusinessRules = new UserBusinessRules(userRepository, messageService);
        userService = new UserServiceImpl(userRepository, userMapper, passwordEncoder, userBusinessRules, jwtService);

        roleRepository = mock(RoleRepository.class);

        roleMapper = new RoleMapperImpl();

        roleBusinessRules = new RoleBusinessRules(roleRepository,messageService);

        roleService = new RoleServiceImpl(roleRepository,roleMapper,roleBusinessRules);

        userRoleRepository= mock(UserRoleRepository.class);

        userRoleMapper = new UserRoleMapperImpl();

        userRoleBusinessRules = new UserRoleBusinessRules(userRoleRepository,messageService);

        userRoleServiceImpl = new UserRoleServiceImpl(userRoleRepository,userRoleMapper,userRoleBusinessRules,userService,roleService);

    }

    @Test
    void add_ShouldCreateUserRole() {
        // Arrange
        UUID userId = UUID.randomUUID();
        UUID roleId = UUID.randomUUID();
        UserRole userRole = new UserRole();
        userRole.setUser(new User());
        userRole.setRole(new Roles(roleId));

        CreateUserRoleRequest createUserRoleRequest = new CreateUserRoleRequest(userId, roleId);
        UserRole createdUserRole = new UserRole();
        createdUserRole.setUser(new User());
        createdUserRole.setRole(new Roles(roleId));
        CreateUserRoleResponse createUserRoleResponse = new CreateUserRoleResponse(createdUserRole.getId(),userId,roleId);

        when(userRoleRepository.save(any(UserRole.class))).thenReturn(createdUserRole);
        when(userRepository.findById(any())).thenReturn(Optional.of(new User()));

        // Act
        CreateUserRoleResponse response = userRoleServiceImpl.add(createUserRoleRequest);

        // Assert
        assertEquals(createUserRoleResponse.id(), response.id());
    }

    @Test
    void update_ShouldUpdateUserRole() {
        // Arrange
        UUID userRoleId = UUID.randomUUID();
        UUID roleId = UUID.randomUUID();
        UserRole userRole = new UserRole();
        userRole.setId(userRoleId);
        userRole.setRole(new Roles());

        UpdateUserRoleRequest updateUserRoleRequest = new UpdateUserRoleRequest(roleId);
        UserRole updatedUserRole = new UserRole();
        updatedUserRole.setId(userRoleId);
        updatedUserRole.setRole(new Roles(roleId));
        UpdateUserRoleResponse updateUserRoleResponse = new UpdateUserRoleResponse(updatedUserRole.getId(),UUID.randomUUID(),roleId,LocalDateTime.now(),LocalDateTime.now());

        when(userRoleRepository.findById(userRoleId)).thenReturn(Optional.of(userRole));
        when(userRoleRepository.save(any(UserRole.class))).thenReturn(updatedUserRole);
        when(roleRepository.findById(any())).thenReturn(Optional.of(new Roles()));

        // Act
        UpdateUserRoleResponse response = userRoleServiceImpl.update(userRoleId, updateUserRoleRequest);

        // Assert
        assertEquals(updateUserRoleResponse.id(), response.id());
        verify(userRoleRepository).findById(userRoleId);
        verify(userRoleRepository).save(userRole);

    }
}