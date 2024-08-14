package com.atmosware.belatrix.managmentService.business.concretes;

import com.atmosware.belatrix.core.services.JwtService;
import com.atmosware.belatrix.managmentService.business.abstracts.OrganizationService;
import com.atmosware.belatrix.managmentService.business.abstracts.UserService;
import com.atmosware.belatrix.managmentService.business.dto.requests.organization.CreateOrganizationRequest;
import com.atmosware.belatrix.managmentService.business.dto.requests.organization.UpdateOrganizationRequest;
import com.atmosware.belatrix.managmentService.business.dto.responses.organization.CreateOrganizationResponse;
import com.atmosware.belatrix.managmentService.business.dto.responses.organization.DeleteOrganizationResponse;
import com.atmosware.belatrix.managmentService.business.dto.responses.organization.GetAllOrganizationResponse;
import com.atmosware.belatrix.managmentService.business.dto.responses.organization.UpdateOrganizationResponse;
import com.atmosware.belatrix.managmentService.business.mappers.OrganizationMapper;
import com.atmosware.belatrix.managmentService.business.rules.OrganizationBusinessRules;
import com.atmosware.belatrix.managmentService.dataAccess.OrganizationRepository;
import com.atmosware.belatrix.managmentService.entities.concretes.Organization;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
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
        log.info("Add organization method started.");
        this.organizationBusinessRules.organizationNameCanNotBeDuplicated(createOrganizationRequest.organizationName());

        Organization organization = this.organizationMapper.toOrganization(createOrganizationRequest);
        Organization createdOrganization = this.organizationRepository.save(organization);

        this.userService.add(createOrganizationRequest.registerUserDto(),createdOrganization
        );

        return this.organizationMapper.toCreateOrganizationResponse(createdOrganization);
    }
    @Override
    @Transactional
    public UpdateOrganizationResponse updateOrganization(HttpServletRequest request, UpdateOrganizationRequest updateOrganizationRequest){
        log.info("Update organization method for organization role started.");
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
    public UpdateOrganizationResponse updateAdmin(UUID id, UpdateOrganizationRequest updateOrganizationRequest){
        log.info("Update organization method for admin role started.");
        this.organizationBusinessRules.organizationShouldBeExists(id);

        Organization organization = this.organizationRepository.findById(id).get();
        organization.setOrganizationName(updateOrganizationRequest.organizationName());
        organization.setUpdatedDate(LocalDateTime.now());

        return this.organizationMapper.toUpdateOrganizationResponse(this.organizationRepository.save(organization));
    }

    @Override
    @Transactional
    public DeleteOrganizationResponse delete(UUID id) {
        log.info("Delete organization method started.");
        this.organizationBusinessRules.organizationShouldBeExists(id);

        Organization organization = this.organizationRepository.findById(id).get();
        organization.setDeletedDate(LocalDateTime.now());
        this.userService.delete(organization.getUserList());

        return this.organizationMapper.toDeleteOrganizationResponse(organization);
    }

    @Override
    public Page<GetAllOrganizationResponse> getAll(int page, int size) {
        log.info("Get all organizations method started");
        Pageable pageable = PageRequest.of(page,size, Sort.by("id"));

        Page<Organization> organizations = this.organizationRepository.findAll(pageable);

        return organizations.map(this.organizationMapper::toGetAllOrganizationResponse);
    }

    @Override
    public Organization getById(UUID id) {
        return this.organizationRepository.findById(id).get();
    }
}

