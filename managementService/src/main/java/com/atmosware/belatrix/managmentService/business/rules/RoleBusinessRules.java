package com.atmosware.belatrix.managmentService.business.rules;

import com.atmosware.belatrix.managmentService.business.constants.Messages;
import com.atmosware.belatrix.managmentService.core.business.abstracts.MessageService;
import com.atmosware.belatrix.managmentService.core.exceptions.types.BusinessException;
import com.atmosware.belatrix.managmentService.dataAccess.RoleRepository;
import com.atmosware.belatrix.managmentService.entities.concretes.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RoleBusinessRules {
    private final RoleRepository roleRepository;
    private final MessageService messageService;
    public void roleCanNotBeDuplicated(String name){
        Optional<Roles> optional = this.roleRepository.findByName(name);
        if(optional.isPresent()){
            throw new BusinessException(this.messageService.getMessage(Messages.RoleMessages.ROLE_CAN_NOT_BE_DUPLICATED));
        }
    }
}
