package com.atmosware.belatrix.managmentService.business.concretes;

import com.atmosware.belatrix.managmentService.business.abstracts.OrganizationService;
import com.atmosware.belatrix.managmentService.business.dto.requests.CreateOrganizationRequest;
import com.atmosware.belatrix.managmentService.business.dto.responses.CreateOrganizationResponse;
import com.atmosware.belatrix.managmentService.business.mappers.OrganizationMapper;
import com.atmosware.belatrix.managmentService.business.rules.OrganizationBusinessRules;
import com.atmosware.belatrix.managmentService.dataAccess.OrganizationRepository;
import com.atmosware.belatrix.managmentService.entities.concretes.Organization;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrganizationManager implements OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;
    private final OrganizationBusinessRules organizationBusinessRules;
    @Override
    public CreateOrganizationResponse add(CreateOrganizationRequest createOrganizationRequest) {
        this.organizationBusinessRules.organizationNameCanNotBeDuplicated(createOrganizationRequest.organizationName());
        Organization organization = this.organizationMapper.toOrganization(createOrganizationRequest);
        Organization createdOrganization = this.organizationRepository.save(organization);
        return this.organizationMapper.toCreateOrganizationResponse(createdOrganization);
    }
}

