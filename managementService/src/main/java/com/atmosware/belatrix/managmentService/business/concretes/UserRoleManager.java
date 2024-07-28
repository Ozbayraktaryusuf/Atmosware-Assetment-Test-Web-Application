package com.atmosware.belatrix.managmentService.business.concretes;

import com.atmosware.belatrix.managmentService.business.abstracts.RoleService;
import com.atmosware.belatrix.managmentService.business.abstracts.UserRoleService;
import com.atmosware.belatrix.managmentService.business.abstracts.UserService;
import com.atmosware.belatrix.managmentService.business.dto.requests.userRole.CreateUserRoleRequest;
import com.atmosware.belatrix.managmentService.business.dto.requests.userRole.UpdateUserRoleRequest;
import com.atmosware.belatrix.managmentService.business.dto.responses.userRole.CreateUserRoleResponse;
import com.atmosware.belatrix.managmentService.business.dto.responses.userRole.UpdateUserRoleResponse;
import com.atmosware.belatrix.managmentService.business.mappers.UserRoleMapper;
import com.atmosware.belatrix.managmentService.business.rules.UserRoleBusinessRules;
import com.atmosware.belatrix.managmentService.dataAccess.UserRoleRepository;
import com.atmosware.belatrix.managmentService.entities.concretes.Roles;
import com.atmosware.belatrix.managmentService.entities.concretes.UserRole;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserRoleManager implements UserRoleService {
    private final UserRoleRepository userRoleRepository;
    private final UserRoleMapper userRoleMapper;
    private final UserRoleBusinessRules userRoleBusinessRules;
    private final UserService userService;
    private final RoleService roleService;

    @Override
    @Transactional
    public CreateUserRoleResponse add(CreateUserRoleRequest createUserRoleRequest) {
        //TODO: other services business rules(user and role)
        UserRole userRole = this.userRoleMapper.toUserRole(createUserRoleRequest);

        UserRole createdUserRole = this.userRoleRepository.save(userRole);

        return this.userRoleMapper.toCreateUserRoleResponse(createdUserRole);
    }

    @Override
    @Transactional
    public UpdateUserRoleResponse update(UUID id,UpdateUserRoleRequest updateUserRoleRequest) {
        Optional<UserRole> optionalUserRole = this.userRoleRepository.findById(id);
        this.userRoleBusinessRules.userRoleShouldBeExists(optionalUserRole);

        UserRole userRole = optionalUserRole.get();
        userRole.setRole(new Roles(this.roleService.getById(updateUserRoleRequest.roleId()).id()));

        return this.userRoleMapper.toUpdateUserRoleResponse(this.userRoleRepository.save(userRole));
    }
}
