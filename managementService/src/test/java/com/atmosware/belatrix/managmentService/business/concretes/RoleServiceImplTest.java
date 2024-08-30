package com.atmosware.belatrix.managmentService.business.concretes;

import com.atmosware.belatrix.managmentService.business.dto.requests.role.CreateRoleRequest;
import com.atmosware.belatrix.managmentService.business.dto.responses.role.CreateRoleResponse;
import com.atmosware.belatrix.managmentService.business.dto.responses.role.GetByIdRoleResponse;
import com.atmosware.belatrix.managmentService.business.mappers.RoleMapper;
import com.atmosware.belatrix.managmentService.business.mappers.RoleMapperImpl;
import com.atmosware.belatrix.managmentService.business.rules.RoleBusinessRules;
import com.atmosware.belatrix.managmentService.core.business.abstracts.MessageService;
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

    private RoleRepository roleRepository;
    private RoleMapper roleMapper;
    private MessageService messageService;
    private RoleBusinessRules roleBusinessRules;
    private RoleServiceImpl roleServiceImpl;

    @BeforeEach
    void setUp() {
        messageService = mock(MessageService.class);
        roleRepository = mock(RoleRepository.class);

        roleMapper = new RoleMapperImpl();

        roleBusinessRules = new RoleBusinessRules(roleRepository,messageService);

        roleServiceImpl = new RoleServiceImpl(roleRepository,roleMapper,roleBusinessRules);

    }

    @Test
    void createRole_ShouldCreateRoleCorrectly() {
        // Arrange
        CreateRoleRequest createRoleRequest = new CreateRoleRequest("Admin");
        Roles role = roleMapper.toRole(createRoleRequest);


        CreateRoleResponse expectedResponse = new CreateRoleResponse(role.getId(),role.getName(),LocalDateTime.now());

        when(roleRepository.save(any())).thenReturn(role);
        when(roleRepository.findByName(anyString())).thenReturn(Optional.empty());

        // Act
        CreateRoleResponse actualResponse = roleServiceImpl.createRole(createRoleRequest);

        // Assert

        assertEquals(expectedResponse.id(), actualResponse.id());
        assertEquals(expectedResponse.name(),actualResponse.name());
    }

    @Test
    void getById_ShouldReturnRoleCorrectly() {
        // Arrange
        Roles role = new Roles();
        role.setName("Admin");
        role.setId( UUID.randomUUID());

        GetByIdRoleResponse expectedResponse = new GetByIdRoleResponse(role.getId(),role.getName(), LocalDateTime.now(),LocalDateTime.now());

        when(roleRepository.findById(any())).thenReturn(Optional.of(role));

        // Act
        GetByIdRoleResponse actualResponse = roleServiceImpl.getById(role.getId());

        // Assert
        verify(roleRepository).findById(role.getId());

        assertEquals(expectedResponse.name(), actualResponse.name());
        assertEquals(expectedResponse.id(),actualResponse.id());
    }
}
