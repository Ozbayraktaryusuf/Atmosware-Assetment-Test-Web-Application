package com.atmosware.belatrix.managmentService.business.abstracts;

import com.atmosware.belatrix.managmentService.business.dto.requests.CreateUserRoleRequest;
import com.atmosware.belatrix.managmentService.business.dto.requests.UpdateUserRoleRequest;
import com.atmosware.belatrix.managmentService.business.dto.responses.CreateUserRoleResponse;
import com.atmosware.belatrix.managmentService.business.dto.responses.UpdateUserRoleResponse;

import java.util.UUID;

public interface UserRoleService {
    CreateUserRoleResponse add(CreateUserRoleRequest createUserRoleRequest);

    UpdateUserRoleResponse update(UUID id,UpdateUserRoleRequest updateUserRoleRequest);
}
