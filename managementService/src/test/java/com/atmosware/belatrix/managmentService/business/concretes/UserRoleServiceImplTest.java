package com.atmosware.belatrix.managmentService.business.concretes;

import com.atmosware.belatrix.managmentService.business.abstracts.RoleService;
import com.atmosware.belatrix.managmentService.business.abstracts.UserService;
import com.atmosware.belatrix.managmentService.business.dto.requests.userRole.CreateUserRoleRequest;
import com.atmosware.belatrix.managmentService.business.dto.requests.userRole.UpdateUserRoleRequest;
import com.atmosware.belatrix.managmentService.business.dto.responses.role.GetByIdRoleResponse;
import com.atmosware.belatrix.managmentService.business.dto.responses.user.GetByIdUserResponse;
import com.atmosware.belatrix.managmentService.business.dto.responses.userRole.CreateUserRoleResponse;
import com.atmosware.belatrix.managmentService.business.dto.responses.userRole.UpdateUserRoleResponse;
import com.atmosware.belatrix.managmentService.business.mappers.UserRoleMapper;
import com.atmosware.belatrix.managmentService.business.rules.UserRoleBusinessRules;
import com.atmosware.belatrix.managmentService.dataAccess.UserRoleRepository;
import com.atmosware.belatrix.managmentService.entities.concretes.Roles;
import com.atmosware.belatrix.managmentService.entities.concretes.User;
import com.atmosware.belatrix.managmentService.entities.concretes.UserRole;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class UserRoleServiceImplTest {

    @InjectMocks
    private UserRoleServiceImpl userRoleServiceImpl;

    @Mock
    private UserRoleRepository userRoleRepository;

    @Mock
    private UserRoleMapper userRoleMapper;

    @Mock
    private UserRoleBusinessRules userRoleBusinessRules;

    @Mock
    private UserService userService;

    @Mock
    private RoleService roleService;

    public UserRoleServiceImplTest() {
        MockitoAnnotations.openMocks(this);
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

        when(userService.getById(userId)).thenReturn(new GetByIdUserResponse("test",userId, LocalDateTime.now(),LocalDateTime.now(),"test name"));
        when(userRoleMapper.toUserRole(createUserRoleRequest)).thenReturn(userRole);
        when(userRoleRepository.save(any(UserRole.class))).thenReturn(createdUserRole);
        when(userRoleMapper.toCreateUserRoleResponse(createdUserRole)).thenReturn(createUserRoleResponse);

        // Act
        CreateUserRoleResponse response = userRoleServiceImpl.add(createUserRoleRequest);

        // Assert
        assertEquals(createUserRoleResponse.id(), response.id());
        verify(userService).getById(userId);
        verify(userRoleMapper).toUserRole(createUserRoleRequest);
        verify(userRoleRepository).save(userRole);
        verify(userRoleMapper).toCreateUserRoleResponse(createdUserRole);
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
        when(roleService.getById(roleId)).thenReturn(new GetByIdRoleResponse(roleId,"test",LocalDateTime.now(),LocalDateTime.now()));
        when(userRoleRepository.save(any(UserRole.class))).thenReturn(updatedUserRole);
        when(userRoleMapper.toUpdateUserRoleResponse(updatedUserRole)).thenReturn(updateUserRoleResponse);

        // Act
        UpdateUserRoleResponse response = userRoleServiceImpl.update(userRoleId, updateUserRoleRequest);

        // Assert
        assertEquals(updateUserRoleResponse.id(), response.id());
        verify(userRoleRepository).findById(userRoleId);
        verify(userRoleBusinessRules).userRoleShouldBeExists(Optional.of(userRole));
        verify(roleService).getById(roleId);
        verify(userRoleRepository).save(userRole);
        verify(userRoleMapper).toUpdateUserRoleResponse(updatedUserRole);
    }
}