package com.atmosware.belatrix.managmentService.business.abstracts;

import com.atmosware.belatrix.managmentService.business.dto.requests.organization.CreateOrganizationRequest;
import com.atmosware.belatrix.managmentService.business.dto.requests.organization.UpdateOrganizationRequest;
import com.atmosware.belatrix.managmentService.business.dto.responses.organization.CreateOrganizationResponse;
import com.atmosware.belatrix.managmentService.business.dto.responses.organization.UpdateOrganizationResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.util.UUID;

public interface OrganizationService {
    CreateOrganizationResponse add(CreateOrganizationRequest createOrganizationRequest);
    UpdateOrganizationResponse update_organization(HttpServletRequest request, UpdateOrganizationRequest updateOrganizationRequest);

    UpdateOrganizationResponse update_admin(UUID id, UpdateOrganizationRequest updateOrganizationRequest);
}
