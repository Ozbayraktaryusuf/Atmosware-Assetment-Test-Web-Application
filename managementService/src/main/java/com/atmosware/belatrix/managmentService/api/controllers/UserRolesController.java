package com.atmosware.belatrix.managmentService.api.controllers;

import com.atmosware.belatrix.managmentService.business.abstracts.UserRoleService;
import com.atmosware.belatrix.managmentService.business.dto.requests.CreateUserRoleRequest;
import com.atmosware.belatrix.managmentService.business.dto.responses.CreateUserRoleResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("management-service/api/v1/user-role")
public class UserRolesController {
    private final UserRoleService userRoleService;
    @PostMapping
    public CreateUserRoleResponse add(@Valid @RequestBody CreateUserRoleRequest createUserRoleRequest){
     return this.userRoleService.add(createUserRoleRequest);
    }
}
