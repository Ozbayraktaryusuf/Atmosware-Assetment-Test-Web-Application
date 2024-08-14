package com.atmosware.belatrix.managmentService.business.concretes;

import com.atmosware.belatrix.core.services.JwtService;
import com.atmosware.belatrix.managmentService.business.abstracts.InvitationService;
import com.atmosware.belatrix.managmentService.business.abstracts.OrganizationService;
import com.atmosware.belatrix.managmentService.business.dto.dtos.MailInfoDto;
import com.atmosware.belatrix.managmentService.business.dto.requests.invitations.CandidateDto;
import com.atmosware.belatrix.managmentService.business.dto.requests.invitations.InvitationRequest;
import com.atmosware.belatrix.managmentService.entities.concretes.Organization;
import com.atmosware.belatrix.managmentService.grpc.clients.abstracts.ExamClientService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class InvitationManagerTest {

    @InjectMocks
    private InvitationManager invitationManager;

    @Mock
    private OrganizationService organizationService;

    @Mock
    private ExamClientService examClientService;

    @Mock
    private JwtService jwtService;

    @Mock
    private HttpServletRequest httpServletRequest;

    public InvitationManagerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendEmail_ShouldSendEmailSuccessfully() {
        // Arrange
        String token = "Bearer token";
        UUID organizationId = UUID.randomUUID();
        String organizationName = "Test Organization";
        String examName = "Test Exam";
        String startDate = "2024-08-14T10:00:00";
        String endDate = "2024-08-14T12:00:00";
        String duration = "120";

        InvitationRequest invitationRequest = new InvitationRequest(3L,
                List.of(new CandidateDto("candidate@example.com"))
        );

        MailInfoDto mailInfoDto = new MailInfoDto();
        mailInfoDto.setExamName(examName);
        mailInfoDto.setStartDate(startDate);
        mailInfoDto.setEndDate(endDate);
        mailInfoDto.setDuration(duration);

        when(httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(token);
        when(jwtService.getClaims(token.substring(7))).thenReturn(mockClaims(organizationId.toString()));
        when(organizationService.getById(organizationId)).thenReturn(new Organization());
        when(examClientService.getExamForMail(any())).thenReturn(mailInfoDto);

        // Act
        invitationManager.sendEmail(invitationRequest, httpServletRequest);

        // Assert
        verify(examClientService).getExamForMail(invitationRequest.examId());
        verify(organizationService).getById(organizationId);
        verify(jwtService).getClaims(token.substring(7));

        // Optionally verify logs or other interactions
    }

    private Claims mockClaims(String organizationId) {
        Claims claims = mock(Claims.class);
        when(claims.get("organizationId")).thenReturn(organizationId);
        return claims;
    }

    private static class OrganizationResponse {
        private final String organizationName;

        public OrganizationResponse(String organizationName) {
            this.organizationName = organizationName;
        }

        public String getOrganizationName() {
            return organizationName;
        }
    }
}
