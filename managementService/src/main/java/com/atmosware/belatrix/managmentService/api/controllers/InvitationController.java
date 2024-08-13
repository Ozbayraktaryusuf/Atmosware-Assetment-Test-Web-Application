package com.atmosware.belatrix.managmentService.api.controllers;

import com.atmosware.belatrix.managmentService.business.abstracts.InvitationService;
import com.atmosware.belatrix.managmentService.business.dto.requests.invitations.InvitationRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("management-service/api/v1/invitations")
public class InvitationController {
    private final InvitationService invitationService;
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void sendEmail(@Valid @RequestBody InvitationRequest invitationRequest, HttpServletRequest httpServletRequest){
        this.invitationService.sendEmail(invitationRequest,httpServletRequest);
    }
}
