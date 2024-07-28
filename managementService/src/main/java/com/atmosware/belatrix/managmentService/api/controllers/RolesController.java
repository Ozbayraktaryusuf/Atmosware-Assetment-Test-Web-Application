package com.atmosware.belatrix.managmentService.api.controllers;

import com.atmosware.belatrix.managmentService.business.abstracts.RoleService;
import com.atmosware.belatrix.managmentService.business.dto.requests.role.CreateRoleRequest;
import com.atmosware.belatrix.managmentService.business.dto.responses.role.CreateRoleResponse;
import com.atmosware.belatrix.managmentService.business.dto.responses.role.GetByIdRoleResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("management-service/api/v1/roles")
public class RolesController {
    private final RoleService roleService;
    @PostMapping
    public CreateRoleResponse createRole(@Valid @RequestBody CreateRoleRequest createRoleRequest){
        return this.roleService.createRole(createRoleRequest);
    }

    @GetMapping("{id}")
    public GetByIdRoleResponse getById(@PathVariable UUID id){
        return this.roleService.getById(id);
    }
}
