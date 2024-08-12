package com.atmosware.belatrix.managmentService.api.controllers;

import com.atmosware.belatrix.managmentService.business.abstracts.OrganizationService;
import com.atmosware.belatrix.managmentService.business.dto.requests.organization.CreateOrganizationRequest;
import com.atmosware.belatrix.managmentService.business.dto.requests.organization.UpdateOrganizationRequest;
import com.atmosware.belatrix.managmentService.business.dto.responses.organization.CreateOrganizationResponse;
import com.atmosware.belatrix.managmentService.business.dto.responses.organization.DeleteOrganizationResponse;
import com.atmosware.belatrix.managmentService.business.dto.responses.organization.GetAllOrganizationResponse;
import com.atmosware.belatrix.managmentService.business.dto.responses.organization.UpdateOrganizationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("management-service/api/v1/organizations")
public class OrganizationsController {
    private final OrganizationService organizationService;
    @PostMapping
    public CreateOrganizationResponse  add(@Valid @RequestBody CreateOrganizationRequest createOrganizationRequest){
        return this.organizationService.add(createOrganizationRequest);
    }
    @GetMapping()
    public Page<GetAllOrganizationResponse> getAll(@RequestParam(name = "page", defaultValue = "0") int page,
                                                   @RequestParam(name = "size", defaultValue = "10") int size){
        return this.organizationService.getAll(page,size);
    }
    @PutMapping("/admin/{id}")
    public UpdateOrganizationResponse update(@PathVariable UUID id, @Valid @RequestBody UpdateOrganizationRequest updateOrganizationRequest){
        return this.organizationService.update_admin(id,updateOrganizationRequest);
    }

    @PutMapping("/organization")
    public UpdateOrganizationResponse update(@Valid @RequestBody UpdateOrganizationRequest updateOrganizationRequest, HttpServletRequest request){
        return this.organizationService.update_organization(request,updateOrganizationRequest);
    }
    @DeleteMapping("{id}")
    public DeleteOrganizationResponse delete(@PathVariable UUID id){
        return this.organizationService.delete(id);
    }
}
