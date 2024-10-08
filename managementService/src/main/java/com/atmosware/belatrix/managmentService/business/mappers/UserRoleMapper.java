package com.atmosware.belatrix.managmentService.business.mappers;

import com.atmosware.belatrix.managmentService.business.dto.requests.userRole.CreateUserRoleRequest;
import com.atmosware.belatrix.managmentService.business.dto.responses.userRole.CreateUserRoleResponse;
import com.atmosware.belatrix.managmentService.business.dto.responses.userRole.UpdateUserRoleResponse;
import com.atmosware.belatrix.managmentService.core.mappping.MapstructService;
import com.atmosware.belatrix.managmentService.entities.concretes.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructService.class)
public interface UserRoleMapper {
    @Mapping(target = "user.id",source = "userId")
    @Mapping(target = "role.id",source = "roleId")
    UserRole toUserRole(CreateUserRoleRequest createUserRoleRequest);

    @Mapping(target = "userId",source = "user.id")
    @Mapping(target = "roleId",source ="role.id" )
    CreateUserRoleResponse toCreateUserRoleResponse(UserRole userRole);

    @Mapping(target = "roleId",source ="role.id" )
    @Mapping(target = "userId",source = "user.id")
    UpdateUserRoleResponse toUpdateUserRoleResponse(UserRole userRole);
}
