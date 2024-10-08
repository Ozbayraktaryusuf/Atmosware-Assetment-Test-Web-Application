package com.atmosware.belatrix.managmentService.business.mappers;

import com.atmosware.belatrix.managmentService.business.dto.dtos.RegisterUserDto;
import com.atmosware.belatrix.managmentService.business.dto.requests.user.CreateAdminRequest;
import com.atmosware.belatrix.managmentService.business.dto.responses.user.GetByIdUserResponse;
import com.atmosware.belatrix.managmentService.business.dto.responses.user.UpdateUserResponse;
import com.atmosware.belatrix.managmentService.core.mappping.MapstructService;
import com.atmosware.belatrix.managmentService.entities.concretes.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructService.class)
public interface UserMapper {
    User toUser(RegisterUserDto registerUserDto);
    @Mapping(target = "organizationName",source = "organization.organizationName")
    GetByIdUserResponse toGetByIdUserResponse(User user);
    @Mapping(target = "organizationName",source = "organization.organizationName")
    UpdateUserResponse toUpdateUserResponse(User user);
    User toUser(CreateAdminRequest createAdminRequest);
}
