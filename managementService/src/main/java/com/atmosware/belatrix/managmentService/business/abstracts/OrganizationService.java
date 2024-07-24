package com.atmosware.belatrix.managmentService.business.abstracts;

import com.atmosware.belatrix.managmentService.business.dto.requests.CreateOrganizationRequest;
import com.atmosware.belatrix.managmentService.business.dto.responses.CreateOrganizationResponse;

public interface OrganizationService {
    CreateOrganizationResponse add(CreateOrganizationRequest createOrganizationRequest);
}
