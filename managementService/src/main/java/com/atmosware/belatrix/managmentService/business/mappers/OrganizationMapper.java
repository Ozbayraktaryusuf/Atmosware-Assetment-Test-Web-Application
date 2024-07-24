package com.atmosware.belatrix.managmentService.business.mappers;

import com.atmosware.belatrix.managmentService.business.dto.requests.CreateOrganizationRequest;
import com.atmosware.belatrix.managmentService.business.dto.responses.CreateOrganizationResponse;
import com.atmosware.belatrix.managmentService.core.mappping.MapstructService;
import com.atmosware.belatrix.managmentService.entities.concretes.Organization;
import org.mapstruct.Mapper;

@Mapper(config = MapstructService.class)
public interface OrganizationMapper {
    Organization toOrganization(CreateOrganizationRequest createOrganizationRequest);

    CreateOrganizationResponse toCreateOrganizationResponse(Organization organization);
}
