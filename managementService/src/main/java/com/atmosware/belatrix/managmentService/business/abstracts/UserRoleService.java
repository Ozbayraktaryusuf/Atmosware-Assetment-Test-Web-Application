package com.atmosware.belatrix.managmentService.business.abstracts;

import com.atmosware.belatrix.managmentService.business.dto.requests.userRole.CreateUserRoleRequest;
import com.atmosware.belatrix.managmentService.business.dto.requests.userRole.UpdateUserRoleRequest;
import com.atmosware.belatrix.managmentService.business.dto.responses.userRole.CreateUserRoleResponse;
import com.atmosware.belatrix.managmentService.business.dto.responses.userRole.UpdateUserRoleResponse;
import com.atmosware.belatrix.managmentService.entities.concretes.User;
import com.atmosware.belatrix.managmentService.entities.concretes.UserRole;

import java.util.List;
import java.util.UUID;

public interface UserRoleService {
    CreateUserRoleResponse add(CreateUserRoleRequest createUserRoleRequest);

    UpdateUserRoleResponse update(UUID id,UpdateUserRoleRequest updateUserRoleRequest);
}
