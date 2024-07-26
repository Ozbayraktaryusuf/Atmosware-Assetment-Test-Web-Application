package com.atmosware.belatrix.managmentService.business.concretes;

import com.atmosware.belatrix.managmentService.business.abstracts.UserRoleService;
import com.atmosware.belatrix.managmentService.business.dto.requests.CreateUserRoleRequest;
import com.atmosware.belatrix.managmentService.business.dto.responses.CreateUserRoleResponse;
import com.atmosware.belatrix.managmentService.business.mappers.UserRoleMapper;
import com.atmosware.belatrix.managmentService.dataAccess.UserRoleRepository;
import com.atmosware.belatrix.managmentService.entities.concretes.UserRole;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserRoleManager implements UserRoleService {
    private final UserRoleRepository userRoleRepository;
    private final UserRoleMapper userRoleMapper;

    @Override
    @Transactional
    public CreateUserRoleResponse add(CreateUserRoleRequest createUserRoleRequest) {
        //TODO: other services business rules(user and role)
        UserRole userRole = this.userRoleMapper.toUserRole(createUserRoleRequest);

        UserRole createdUserRole = this.userRoleRepository.save(userRole);

        return this.userRoleMapper.toCreateUserRoleResponse(createdUserRole);
    }
}
