package com.atmosware.belatrix.managmentService.business.abstracts;

import com.atmosware.belatrix.managmentService.business.dto.dtos.RegisterUserDto;
import com.atmosware.belatrix.managmentService.business.dto.requests.user.CreateAdminRequest;
import com.atmosware.belatrix.managmentService.business.dto.requests.user.UpdateUserRequest;
import com.atmosware.belatrix.managmentService.business.dto.responses.user.GetByIdUserResponse;
import com.atmosware.belatrix.managmentService.business.dto.responses.user.UpdateUserResponse;
import com.atmosware.belatrix.managmentService.entities.concretes.Organization;
import com.atmosware.belatrix.managmentService.entities.concretes.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.UUID;

public interface UserService extends UserDetailsService {
    void add(RegisterUserDto registerUserDto, Organization organization);
    void add(RegisterUserDto registerUserDto, UUID organizationId);
    void createAdmin(CreateAdminRequest createAdminRequest);
    GetByIdUserResponse getById(UUID id);
    User findByEmail(String username);
    UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest, HttpServletRequest httpServletRequest);
}
