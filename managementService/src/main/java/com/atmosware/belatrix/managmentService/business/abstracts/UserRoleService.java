package com.atmosware.belatrix.managmentService.business.abstracts;

import com.atmosware.belatrix.managmentService.business.dto.requests.CreateUserRoleRequest;
import com.atmosware.belatrix.managmentService.business.dto.responses.CreateUserRoleResponse;

public interface UserRoleService {
    CreateUserRoleResponse add(CreateUserRoleRequest createUserRoleRequest);
}
