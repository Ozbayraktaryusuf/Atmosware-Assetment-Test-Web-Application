package com.atmosware.belatrix.managmentService.business.rules;

import com.atmosware.belatrix.managmentService.business.constants.Messages;
import com.atmosware.belatrix.managmentService.core.business.abstracts.MessageService;
import com.atmosware.belatrix.managmentService.core.exceptions.types.AuthenticationException;
import com.atmosware.belatrix.managmentService.dataAccess.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthBusinessRules {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final MessageService messageService;
    //TODO: Don't return wanted message take a look
    public void emailAndPasswordShouldBeMatch(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        if (!authentication.isAuthenticated()) {
            throw new AuthenticationException(messageService.getMessage(Messages.AuthenticationMessages.INVALID_USER_OR_PASSWORD));
        }
    }
}
