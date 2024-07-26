package com.atmosware.belatrix.managmentService.business.abstracts;

import com.atmosware.belatrix.managmentService.business.dto.dtos.RegisterUserDto;
import com.atmosware.belatrix.managmentService.business.dto.requests.UpdateUserRequest;
import com.atmosware.belatrix.managmentService.business.dto.responses.GetByIdUserResponse;
import com.atmosware.belatrix.managmentService.business.dto.responses.UpdateUserResponse;
import com.atmosware.belatrix.managmentService.entities.concretes.Organization;
import com.atmosware.belatrix.managmentService.entities.concretes.User;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.UUID;

public interface UserService extends UserDetailsService {
    void addOrganization(RegisterUserDto registerUserDto, Organization organization);
    GetByIdUserResponse getById(UUID id);
    User findByEmail(String username);

    UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest, HttpServletRequest httpServletRequest);
}
