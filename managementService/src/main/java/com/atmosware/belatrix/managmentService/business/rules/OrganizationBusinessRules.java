package com.atmosware.belatrix.managmentService.business.rules;

import com.atmosware.belatrix.managmentService.business.constants.Messages;
import com.atmosware.belatrix.managmentService.core.business.abstracts.MessageService;
import com.atmosware.belatrix.managmentService.core.business.concretes.MessageManager;
import com.atmosware.belatrix.managmentService.core.exceptions.types.BusinessException;
import com.atmosware.belatrix.managmentService.core.exceptions.types.NotFoundException;
import com.atmosware.belatrix.managmentService.dataAccess.OrganizationRepository;
import com.atmosware.belatrix.managmentService.entities.concretes.Organization;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrganizationBusinessRules {
    private final MessageService messageService;
    private final OrganizationRepository organizationRepository;

    public void organizationNameCanNotBeDuplicated(String organizationName) {
        Optional<Organization> optionalOrganization = this.organizationRepository.findByOrganizationName(organizationName);
        if (optionalOrganization.isPresent()) {
            throw new BusinessException(messageService.getMessage(Messages.OrganizationMessages.ORGANIZATION_NAME_CAN_NOT_BE_DUPLICATED));
        }
    }

    public void organizationShouldBeExists(UUID id) {
        Optional<Organization> optionalOrganization = this.organizationRepository.findById(id);
        if (optionalOrganization.isEmpty()) {
            throw new NotFoundException(messageService.getMessage(Messages.OrganizationMessages.ORGANIZATION_SHOULD_BE_EXISTS));
        }
    }
}
