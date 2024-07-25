package com.atmosware.belatrix.managmentService.business.concretes;

import com.atmosware.belatrix.managmentService.business.abstracts.RoleService;
import com.atmosware.belatrix.managmentService.business.abstracts.UserService;
import com.atmosware.belatrix.managmentService.business.dto.requests.CreateRoleRequest;
import com.atmosware.belatrix.managmentService.business.dto.responses.CreateRoleResponse;
import com.atmosware.belatrix.managmentService.business.mappers.RoleMapper;
import com.atmosware.belatrix.managmentService.business.rules.RoleBusinessRules;
import com.atmosware.belatrix.managmentService.dataAccess.RoleRepository;
import com.atmosware.belatrix.managmentService.entities.concretes.Roles;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Locale;

@RequiredArgsConstructor
@Service
public class RoleManager implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final RoleBusinessRules roleBusinessRules;

    @Override
    @Transactional
    public CreateRoleResponse createRole(CreateRoleRequest createRoleRequest) {
        this.roleBusinessRules.roleCanNotBeDuplicated(createRoleRequest.name().toLowerCase(Locale.ROOT));

        Roles role = this.roleMapper.toRole(createRoleRequest);

        role.setName(role.getName().toLowerCase(Locale.ROOT));

        Roles createdRole = this.roleRepository.save(role);

        return this.roleMapper.toCreateRoleResponse(createdRole);
    }
}
