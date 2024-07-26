package com.atmosware.belatrix.managmentService.business.mappers;

import com.atmosware.belatrix.managmentService.business.dto.requests.CreateRoleRequest;
import com.atmosware.belatrix.managmentService.business.dto.responses.CreateRoleResponse;
import com.atmosware.belatrix.managmentService.business.dto.responses.GetByIdRoleResponse;
import com.atmosware.belatrix.managmentService.core.mappping.MapstructService;
import com.atmosware.belatrix.managmentService.entities.concretes.Roles;
import org.mapstruct.Mapper;


@Mapper(config = MapstructService.class)
public interface RoleMapper {
    Roles toRole(CreateRoleRequest createRoleRequest);

    CreateRoleResponse toCreateRoleResponse(Roles role);

    GetByIdRoleResponse toGetByIdRoleResponse(Roles role);
}
