package com.atmosware.belatrix.managmentService.api.controllers;

import com.atmosware.belatrix.managmentService.business.abstracts.UserRoleService;
import com.atmosware.belatrix.managmentService.business.dto.requests.userRole.CreateUserRoleRequest;
import com.atmosware.belatrix.managmentService.business.dto.requests.userRole.UpdateUserRoleRequest;
import com.atmosware.belatrix.managmentService.business.dto.responses.userRole.CreateUserRoleResponse;
import com.atmosware.belatrix.managmentService.business.dto.responses.userRole.UpdateUserRoleResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("management-service/api/v1/user-role")
public class UserRolesController {
    private final UserRoleService userRoleService;
    @PostMapping
    public CreateUserRoleResponse add(@Valid @RequestBody CreateUserRoleRequest createUserRoleRequest){
     return this.userRoleService.add(createUserRoleRequest);
    }

    @PutMapping("{id}")
    public UpdateUserRoleResponse update(@PathVariable UUID id, @Valid @RequestBody UpdateUserRoleRequest updateUserRoleRequest){
        return this.userRoleService.update(id,updateUserRoleRequest);
    }
}
