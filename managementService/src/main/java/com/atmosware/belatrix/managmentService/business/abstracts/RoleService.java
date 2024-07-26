package com.atmosware.belatrix.managmentService.business.abstracts;

import com.atmosware.belatrix.managmentService.business.dto.requests.CreateRoleRequest;
import com.atmosware.belatrix.managmentService.business.dto.responses.CreateRoleResponse;
import com.atmosware.belatrix.managmentService.business.dto.responses.GetByIdRoleResponse;

import java.util.UUID;

public interface RoleService {
    CreateRoleResponse createRole(CreateRoleRequest createRoleRequest);
    GetByIdRoleResponse getById(UUID id);
}
