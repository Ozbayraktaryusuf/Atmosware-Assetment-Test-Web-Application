package com.atmosware.belatrix.managmentService.business.abstracts;

import com.atmosware.belatrix.managmentService.business.dto.requests.role.CreateRoleRequest;
import com.atmosware.belatrix.managmentService.business.dto.responses.role.CreateRoleResponse;
import com.atmosware.belatrix.managmentService.business.dto.responses.role.GetByIdRoleResponse;

import java.util.UUID;

public interface RoleService {
    CreateRoleResponse createRole(CreateRoleRequest createRoleRequest);
    GetByIdRoleResponse getById(UUID id);

}
