package com.atmosware.belatrix.managmentService.business.abstracts;

import com.atmosware.belatrix.managmentService.business.dto.requests.invitations.InvitationRequest;
import jakarta.servlet.http.HttpServletRequest;

public interface InvitationService {
    void sendEmail(InvitationRequest invitationRequest, HttpServletRequest httpServletRequest);
}
