package com.atmosware.belatrix.managmentService.business.concretes;

import com.atmosware.belatrix.managmentService.business.abstracts.OrganizationService;
import com.atmosware.belatrix.managmentService.business.abstracts.UserService;
import com.atmosware.belatrix.managmentService.business.dto.requests.organization.CreateOrganizationRequest;
import com.atmosware.belatrix.managmentService.business.dto.requests.organization.UpdateOrganizationRequest;
import com.atmosware.belatrix.managmentService.business.dto.responses.organization.CreateOrganizationResponse;
import com.atmosware.belatrix.managmentService.business.dto.responses.organization.UpdateOrganizationResponse;
import com.atmosware.belatrix.managmentService.business.mappers.OrganizationMapper;
import com.atmosware.belatrix.managmentService.business.rules.OrganizationBusinessRules;
import com.atmosware.belatrix.managmentService.core.service.JwtService;
import com.atmosware.belatrix.managmentService.dataAccess.OrganizationRepository;
import com.atmosware.belatrix.managmentService.entities.concretes.Organization;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrganizationManager implements OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;
    private final OrganizationBusinessRules organizationBusinessRules;
    private final UserService userService;
    private final JwtService jwtService;
    @Override
    @Transactional
    public CreateOrganizationResponse add(CreateOrganizationRequest createOrganizationRequest) {
        this.organizationBusinessRules.organizationNameCanNotBeDuplicated(createOrganizationRequest.organizationName());

        Organization organization = this.organizationMapper.toOrganization(createOrganizationRequest);
        Organization createdOrganization = this.organizationRepository.save(organization);

        this.userService.addOrganization(createOrganizationRequest.registerUserDto(),createdOrganization
        );

        return this.organizationMapper.toCreateOrganizationResponse(createdOrganization);
    }
    @Override
    @Transactional
    public UpdateOrganizationResponse update_organization(HttpServletRequest request, UpdateOrganizationRequest updateOrganizationRequest){
        String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        UUID id = UUID.fromString(jwtService.getClaims(token).get("organizationId").toString());

        this.organizationBusinessRules.organizationShouldBeExists(id);

        Organization organization = this.organizationRepository.findById(id).get();
        organization.setOrganizationName(updateOrganizationRequest.organizationName());
        organization.setUpdatedDate(LocalDateTime.now());

        return this.organizationMapper.toUpdateOrganizationResponse(this.organizationRepository.save(organization));
    }
    @Override
    @Transactional
    public UpdateOrganizationResponse update_admin(UUID id,UpdateOrganizationRequest updateOrganizationRequest){
        this.organizationBusinessRules.organizationShouldBeExists(id);

        Organization organization = this.organizationRepository.findById(id).get();
        organization.setOrganizationName(updateOrganizationRequest.organizationName());
        organization.setUpdatedDate(LocalDateTime.now());

        return this.organizationMapper.toUpdateOrganizationResponse(this.organizationRepository.save(organization));
    }
}

