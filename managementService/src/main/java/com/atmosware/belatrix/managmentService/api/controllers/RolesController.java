package com.atmosware.belatrix.managmentService.api.controllers;

import com.atmosware.belatrix.managmentService.business.abstracts.RoleService;
import com.atmosware.belatrix.managmentService.business.dto.requests.CreateRoleRequest;
import com.atmosware.belatrix.managmentService.business.dto.responses.CreateRoleResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("management-service/api/v1/roles")
public class RolesController {
    private final RoleService roleService;
    @PostMapping
    public CreateRoleResponse createRole(@Valid @RequestBody CreateRoleRequest createRoleRequest){
        return this.roleService.createRole(createRoleRequest);
    }
}
