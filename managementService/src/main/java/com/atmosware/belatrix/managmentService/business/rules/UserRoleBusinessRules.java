package com.atmosware.belatrix.managmentService.business.rules;

import com.atmosware.belatrix.managmentService.business.constants.Messages;
import com.atmosware.belatrix.managmentService.core.business.abstracts.MessageService;
import com.atmosware.belatrix.managmentService.core.exceptions.types.NotFoundException;
import com.atmosware.belatrix.managmentService.dataAccess.UserRoleRepository;
import com.atmosware.belatrix.managmentService.entities.concretes.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRoleBusinessRules {
    private final UserRoleRepository userRoleRepository;
    private final MessageService messageService;
    public void userRoleShouldBeExists(Optional<UserRole> optionalUserRole){
        if (optionalUserRole.isEmpty()){
            throw new NotFoundException(Messages.UserRoleMessages.USER_ROLE_SHOULD_BE_EXISTS);
        }
    }
}
