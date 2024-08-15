package com.atmosware.belatrix.managmentService.business.concretes;

import com.atmosware.belatrix.managmentService.business.dto.requests.role.CreateRoleRequest;
import com.atmosware.belatrix.managmentService.business.dto.responses.role.CreateRoleResponse;
import com.atmosware.belatrix.managmentService.business.dto.responses.role.GetByIdRoleResponse;
import com.atmosware.belatrix.managmentService.business.mappers.RoleMapper;
import com.atmosware.belatrix.managmentService.business.rules.RoleBusinessRules;
import com.atmosware.belatrix.managmentService.dataAccess.RoleRepository;
import com.atmosware.belatrix.managmentService.entities.concretes.Roles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private RoleMapper roleMapper;

    @Mock
    private RoleBusinessRules roleBusinessRules;

    @InjectMocks
    private RoleServiceImpl roleServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createRole_ShouldCreateRoleCorrectly() {
        // Arrange
        CreateRoleRequest createRoleRequest = new CreateRoleRequest("Admin");
        Roles role = new Roles();
        role.setName("admin");

        Roles createdRole = new Roles();
        createdRole.setName("admin");

        CreateRoleResponse expectedResponse = new CreateRoleResponse(role.getId(),role.getName(),LocalDateTime.now());

        when(roleMapper.toRole(createRoleRequest)).thenReturn(role);
        when(roleRepository.save(role)).thenReturn(createdRole);
        when(roleMapper.toCreateRoleResponse(createdRole)).thenReturn(expectedResponse);

        // Act
        CreateRoleResponse actualResponse = roleServiceImpl.createRole(createRoleRequest);

        // Assert
        verify(roleBusinessRules).roleCanNotBeDuplicated(createRoleRequest.name().toLowerCase(Locale.ROOT));
        verify(roleMapper).toRole(createRoleRequest);
        verify(roleRepository).save(role);
        verify(roleMapper).toCreateRoleResponse(createdRole);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void getById_ShouldReturnRoleCorrectly() {
        // Arrange
        UUID roleId = UUID.randomUUID();
        Roles role = new Roles();
        role.setName("Admin");

        GetByIdRoleResponse expectedResponse = new GetByIdRoleResponse(roleId,role.getName(), LocalDateTime.now(),LocalDateTime.now());

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));
        when(roleMapper.toGetByIdRoleResponse(role)).thenReturn(expectedResponse);

        // Act
        GetByIdRoleResponse actualResponse = roleServiceImpl.getById(roleId);

        // Assert
        verify(roleRepository).findById(roleId);
        verify(roleMapper).toGetByIdRoleResponse(role);

        assertEquals(expectedResponse, actualResponse);
    }
}
