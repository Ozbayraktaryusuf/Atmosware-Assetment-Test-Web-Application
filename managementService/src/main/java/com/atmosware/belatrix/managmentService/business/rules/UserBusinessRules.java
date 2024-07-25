package com.atmosware.belatrix.managmentService.business.rules;

import com.atmosware.belatrix.managmentService.business.constants.Messages;
import com.atmosware.belatrix.managmentService.core.business.abstracts.MessageService;
import com.atmosware.belatrix.managmentService.core.exceptions.types.BusinessException;
import com.atmosware.belatrix.managmentService.core.exceptions.types.NotFoundException;
import com.atmosware.belatrix.managmentService.dataAccess.UserRepository;
import com.atmosware.belatrix.managmentService.entities.concretes.Roles;
import com.atmosware.belatrix.managmentService.entities.concretes.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserBusinessRules {
    private final UserRepository userRepository;
    private final MessageService messageService;
    public void userCanNotBeDuplicated(String email){
        Optional<User> optional = this.userRepository.findByEmail(email);
        if(optional.isPresent()){
            throw new BusinessException(this.messageService.getMessage(Messages.UserMessages.USER_CAN_NOT_BE_DUPLICATED));
        }
    }

    public void userShouldBeExists(Optional<User> optionalUser){
        if (optionalUser.isEmpty()){
            throw new NotFoundException(this.messageService.getMessage(Messages.UserMessages.USER_SHOULD_BE_EXISTS));
        }
    }
}
