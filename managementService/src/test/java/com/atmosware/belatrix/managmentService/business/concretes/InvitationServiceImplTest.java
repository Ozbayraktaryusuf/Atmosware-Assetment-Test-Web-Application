package com.atmosware.belatrix.managmentService.business.concretes;

import com.atmosware.belatrix.core.services.JwtService;
import com.atmosware.belatrix.managmentService.business.abstracts.OrganizationService;
import com.atmosware.belatrix.managmentService.business.dto.dtos.MailInfoDto;
import com.atmosware.belatrix.managmentService.business.dto.requests.invitations.CandidateDto;
import com.atmosware.belatrix.managmentService.business.dto.requests.invitations.InvitationRequest;
import com.atmosware.belatrix.managmentService.entities.concretes.Organization;
import com.atmosware.belatrix.managmentService.entities.concretes.User;
import com.atmosware.belatrix.managmentService.grpc.client.abstracts.ExamClientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class InvitationServiceImplTest {

    @InjectMocks
    private InvitationServiceImpl invitationServiceImpl;

    @Mock
    private OrganizationService organizationService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    private ExamClientService examClientService;

    @Mock
    private JwtService jwtService;

    @Mock
    private HttpServletRequest httpServletRequest;

    public InvitationServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendEmail_ShouldSendEmailSuccessfully() throws JsonProcessingException {
        // Arrange
        UUID organizationId = UUID.randomUUID();
        String organizationName = "Test Organization";
        String examName = "Test Exam";
        String startDate = "2024-08-14T10:00:00";
        String endDate = "2024-08-14T12:00:00";
        String duration = "120";

        InvitationRequest invitationRequest = new InvitationRequest(3L,
                List.of(new CandidateDto("candidate@example.com"))
        );

        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJvcmdhbml6YXRpb25JZCI6ImY5YzZkODM0LWJlYjktNDY5Yy05MjdjLTIyMzQ0MDM2MzNhYSIsInJvbGVzIjpbIm9yZ2FuaXphdGlvbiJdLCJpZCI6ImRlYzg1MzRhLTBkZmQtNDIwMS1iMGFjLTY2YWY1OWUzNjU5MCIsInN1YiI6InN0cmluZ0BzdHJpbmcuY29tIiwiaWF0IjoxNzI0ODU3NDMzLCJleHAiOjE3MjQ4NTgwMzN9.eQyJVW17dfPhun7CR-eZKQzSktrSGWs7bYC2XXfjqnU";
        String encodedJwt = token.split(" ")[1];
        String[] tokenParts = encodedJwt.split("\\.");
        String payloadPart = tokenParts[1];
        Base64.Decoder decoder = Base64.getDecoder();
        String payload = new String(decoder.decode(payloadPart));
        HashMap hashMap = objectMapper.readValue(payload, HashMap.class);
        Claims claims = new DefaultClaims(hashMap);

        MailInfoDto mailInfoDto = new MailInfoDto();
        mailInfoDto.setExamName(examName);
        mailInfoDto.setStartDate(startDate);
        mailInfoDto.setEndDate(endDate);
        mailInfoDto.setDuration(duration);

        when(httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(token);
        when(jwtService.getClaims(encodedJwt)).thenReturn(claims);
        when(organizationService.getById(any())).thenReturn(new Organization(organizationName,List.of(new User(),new User())));
        when(examClientService.getExamForMail(any())).thenReturn(mailInfoDto);

        // Act
        invitationServiceImpl.sendEmail(invitationRequest, httpServletRequest);

        // Assert
        verify(examClientService).getExamForMail(invitationRequest.examId());
        verify(jwtService).getClaims(encodedJwt);

        // Optionally verify logs or other interactions
    }

}
