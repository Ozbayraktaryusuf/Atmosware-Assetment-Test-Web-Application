package com.atmosware.belatrix.managmentService.business.abstracts;

import com.atmosware.belatrix.managmentService.business.dto.requests.CreateRoleRequest;
import com.atmosware.belatrix.managmentService.business.dto.responses.CreateRoleResponse;

public interface RoleService {
    CreateRoleResponse createRole(CreateRoleRequest createRoleRequest);
}
