package com.atmosware.belatrix.managmentService.api.controllers;

import com.atmosware.belatrix.managmentService.business.abstracts.OrganizationService;
import com.atmosware.belatrix.managmentService.business.dto.requests.CreateOrganizationRequest;
import com.atmosware.belatrix.managmentService.business.dto.responses.CreateOrganizationResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("management-service/api/v1/organizations")
public class OrganizationsController {
    private final OrganizationService organizationService;
    @PostMapping
    public CreateOrganizationResponse  add(@Valid @RequestBody CreateOrganizationRequest createOrganizationRequest){
        return this.organizationService.add(createOrganizationRequest);
    }
}
