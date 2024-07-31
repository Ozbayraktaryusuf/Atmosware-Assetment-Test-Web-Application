package com.atmosware.belatrix.managmentService.business.mappers;

import com.atmosware.belatrix.managmentService.business.dto.requests.organization.CreateOrganizationRequest;
import com.atmosware.belatrix.managmentService.business.dto.requests.organization.UpdateOrganizationRequest;
import com.atmosware.belatrix.managmentService.business.dto.responses.organization.CreateOrganizationResponse;
import com.atmosware.belatrix.managmentService.business.dto.responses.organization.DeleteOrganizationResponse;
import com.atmosware.belatrix.managmentService.business.dto.responses.organization.UpdateOrganizationResponse;
import com.atmosware.belatrix.managmentService.core.mappping.MapstructService;
import com.atmosware.belatrix.managmentService.entities.concretes.Organization;
import org.aspectj.weaver.ast.Or;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructService.class)
public interface OrganizationMapper {
    Organization toOrganization(CreateOrganizationRequest createOrganizationRequest);

    CreateOrganizationResponse toCreateOrganizationResponse(Organization organization);
    //TODO:Update ederken mapppingte sıkıntı oluyor manuel mapplemek zorunda kalıyorsun  buraya dön bak.
    Organization toOrganization(UpdateOrganizationRequest updateOrganizationRequest);

    UpdateOrganizationResponse toUpdateOrganizationResponse(Organization organization);

    DeleteOrganizationResponse toDeleteOrganizationResponse(Organization organization);

}
