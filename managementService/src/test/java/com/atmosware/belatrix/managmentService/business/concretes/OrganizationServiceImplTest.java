package com.atmosware.belatrix.managmentService.business.concretes;

import com.atmosware.belatrix.core.services.JwtService;
import com.atmosware.belatrix.managmentService.business.abstracts.UserService;
import com.atmosware.belatrix.managmentService.business.dto.dtos.RegisterUserDto;
import com.atmosware.belatrix.managmentService.business.dto.requests.organization.CreateOrganizationRequest;
import com.atmosware.belatrix.managmentService.business.dto.requests.organization.UpdateOrganizationRequest;
import com.atmosware.belatrix.managmentService.business.dto.responses.organization.CreateOrganizationResponse;
import com.atmosware.belatrix.managmentService.business.dto.responses.organization.DeleteOrganizationResponse;
import com.atmosware.belatrix.managmentService.business.dto.responses.organization.GetAllOrganizationResponse;
import com.atmosware.belatrix.managmentService.business.dto.responses.organization.UpdateOrganizationResponse;
import com.atmosware.belatrix.managmentService.business.mappers.OrganizationMapper;
import com.atmosware.belatrix.managmentService.business.mappers.OrganizationMapperImpl;
import com.atmosware.belatrix.managmentService.business.mappers.UserMapper;
import com.atmosware.belatrix.managmentService.business.mappers.UserMapperImpl;
import com.atmosware.belatrix.managmentService.business.rules.OrganizationBusinessRules;
import com.atmosware.belatrix.managmentService.business.rules.UserBusinessRules;
import com.atmosware.belatrix.managmentService.core.business.abstracts.MessageService;
import com.atmosware.belatrix.managmentService.dataAccess.OrganizationRepository;
import com.atmosware.belatrix.managmentService.dataAccess.UserRepository;
import com.atmosware.belatrix.managmentService.entities.concretes.Organization;
import com.atmosware.belatrix.managmentService.entities.concretes.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.*;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OrganizationServiceImplTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private OrganizationRepository organizationRepository;
    private OrganizationMapper organizationMapper;
    private OrganizationBusinessRules organizationBusinessRules;
    private UserService userService;
    private UserRepository userRepository;
    private MessageService messageService;
    private JwtService jwtService;
    private OrganizationServiceImpl organizationService;

    @BeforeEach
    void setUp() {
        jwtService = mock(JwtService.class);
        userRepository = mock(UserRepository.class);
        messageService = mock(MessageService.class);
        organizationRepository = mock(OrganizationRepository.class);

        UserMapper userMapper = new UserMapperImpl();
        organizationMapper = new OrganizationMapperImpl();

        UserBusinessRules userBusinessRules = new UserBusinessRules(userRepository, messageService);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        userService = new UserServiceImpl(userRepository, userMapper, passwordEncoder, userBusinessRules, jwtService);

        organizationBusinessRules = new OrganizationBusinessRules(messageService, organizationRepository);
        organizationService = new OrganizationServiceImpl(organizationRepository, organizationMapper, organizationBusinessRules, userService, jwtService);
    }

    @Test
    void add_ShouldAddOrganizationCorrectly() {
        // Arrange
        CreateOrganizationRequest request = new CreateOrganizationRequest("Test Organization", new RegisterUserDto("user@example.com", "password"));
        Organization organization = organizationMapper.toOrganization(request);
        //Organization createdOrganization = organization;
        organization.setId(UUID.randomUUID());
        CreateOrganizationResponse expectedResponse = new CreateOrganizationResponse(organization.getId(), organization.getOrganizationName(), LocalDate.now());

        when(organizationRepository.save(any())).thenReturn(organization);

        // Act
        CreateOrganizationResponse response = organizationService.add(request);

        // Assert

        assertEquals(expectedResponse.id(), response.id());
        assertEquals(expectedResponse.organizationName(), response.organizationName());
    }

    @Test
    void updateOrganization_ShouldUpdateOrganizationCorrectly() throws JsonProcessingException {
        // Arrange
        UpdateOrganizationRequest updateRequest = new UpdateOrganizationRequest("Updated Organization Name");
        Organization organization = organizationMapper.toOrganization(updateRequest);
        organization.setId(UUID.randomUUID());
        HttpServletRequest request = mock(HttpServletRequest.class);

        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJvcmdhbml6YXRpb25JZCI6ImY5YzZkODM0LWJlYjktNDY5Yy05MjdjLTIyMzQ0MDM2MzNhYSIsInJvbGVzIjpbIm9yZ2FuaXphdGlvbiJdLCJpZCI6ImRlYzg1MzRhLTBkZmQtNDIwMS1iMGFjLTY2YWY1OWUzNjU5MCIsInN1YiI6InN0cmluZ0BzdHJpbmcuY29tIiwiaWF0IjoxNzI0ODU3NDMzLCJleHAiOjE3MjQ4NTgwMzN9.eQyJVW17dfPhun7CR-eZKQzSktrSGWs7bYC2XXfjqnU";
        String encodedJwt = token.split(" ")[1];
        String[] tokenParts = encodedJwt.split("\\.");
        String payloadPart = tokenParts[1];
        Base64.Decoder decoder = Base64.getDecoder();
        String payload = new String(decoder.decode(payloadPart));
        HashMap hashMap = objectMapper.readValue(payload, HashMap.class);
        Claims claims = new DefaultClaims(hashMap);

        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(token);

        when(jwtService.getClaims(encodedJwt)).thenReturn(claims);

        when(organizationRepository.findById(any())).thenReturn(Optional.of(organization));
        when(organizationRepository.save(any())).thenReturn(organization);

        // Act
        UpdateOrganizationResponse response = organizationService.updateOrganization(request, updateRequest);

        // Assert
        verify(organizationRepository).save(organization);
        assertEquals(organization.getId(), response.id());
        assertEquals(organization.getOrganizationName(), response.organizationName());
    }

    @Test
    void updateAdmin_ShouldUpdateOrganizationForAdminCorrectly() {
        // Arrange
        UpdateOrganizationRequest updateRequest = new UpdateOrganizationRequest("Updated Organization Name");
        Organization organization = organizationMapper.toOrganization(updateRequest);
        organization.setId(UUID.randomUUID());

        when(organizationRepository.findById(any())).thenReturn(Optional.of(organization));
        when(organizationRepository.save(any())).thenReturn(organization);

        // Act
        UpdateOrganizationResponse response = organizationService.updateAdmin(organization.getId(), updateRequest);

        // Assert
        verify(organizationRepository).save(organization);
        assertEquals(organization.getId(), response.id());
        assertEquals(organization.getOrganizationName(), response.organizationName());
    }

    @Test
    void delete_ShouldDeleteOrganizationCorrectly() {
        // Arrange
        UUID organizationId = UUID.randomUUID();
        Organization organization = new Organization();
        organization.setId(organizationId);
        organization.setUserList(List.of(new User(), new User()));

        when(organizationRepository.findById(organizationId)).thenReturn(Optional.of(organization));


        // Act
        DeleteOrganizationResponse response = organizationService.delete(organizationId);

        // Assert
        assertEquals(organizationId, response.id());
        assertEquals(organization.getOrganizationName(), response.organizationName());
    }

    @Test
    void getAll_ShouldReturnAllOrganizationsCorrectly() {
        // Arrange
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        Organization organization = new Organization();
        organization.setId(UUID.randomUUID());
        Page<Organization> organizations = new PageImpl<>(List.of(organization));

        when(organizationRepository.findAll(pageable)).thenReturn(organizations);

        // Act
        Page<GetAllOrganizationResponse> response = organizationService.getAll(page, size);

        // Assert
        assertEquals(1, response.getTotalElements());
        assertEquals(organization.getId(), response.getContent().get(0).id());
    }

    @Test
    void getById_ShouldReturnOrganizationCorrectly() {
        // Arrange
        UUID organizationId = UUID.randomUUID();
        Organization organization = new Organization();
        organization.setId(organizationId);

        when(organizationRepository.findById(organizationId)).thenReturn(Optional.of(organization));

        // Act
        Organization response = organizationService.getById(organizationId);

        // Assert
        assertEquals(organizationId, response.getId());
    }
}

