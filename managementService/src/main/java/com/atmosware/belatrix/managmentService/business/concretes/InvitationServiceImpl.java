package com.atmosware.belatrix.managmentService.business.concretes;

import com.atmosware.belatrix.core.services.JwtService;
import com.atmosware.belatrix.managmentService.business.abstracts.InvitationService;
import com.atmosware.belatrix.managmentService.business.abstracts.OrganizationService;
import com.atmosware.belatrix.managmentService.business.dto.dtos.MailInfoDto;
import com.atmosware.belatrix.managmentService.business.dto.requests.invitations.CandidateDto;
import com.atmosware.belatrix.managmentService.business.dto.requests.invitations.InvitationRequest;
import com.atmosware.belatrix.managmentService.grpc.client.abstracts.ExamClientService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class InvitationServiceImpl implements InvitationService {
    private final OrganizationService organizationService;
    private final ExamClientService examClientService;
    private final JwtService jwtService;
    @Override
    public void sendEmail(InvitationRequest invitationRequest, HttpServletRequest httpServletRequest) {
        log.info("Sending mail to {}",invitationRequest.candidates().stream().map(CandidateDto::email).toList());
        MailInfoDto mailInfoDto = examClientService.getExamForMail(invitationRequest.examId());
        UUID organizationId = UUID.fromString(this.jwtService.
                getClaims(
                        httpServletRequest
                                .getHeader
                                        (HttpHeaders.AUTHORIZATION).substring(7))
                .get("organizationId")
                .toString());
        String organizationName = this.organizationService.getById(organizationId).getOrganizationName();
        for (CandidateDto candidateDto : invitationRequest.candidates()){
            log.info(mailDraft(candidateDto.email(),organizationName,mailInfoDto.getExamName(),
                    mailInfoDto.getStartDate(),mailInfoDto.getEndDate(),mailInfoDto.getDuration()));
        }
        log.info("Mails send.");
    }
    private String mailDraft(String candidate ,String organizationName,String examName,String startDate,String endDate, String duration){
        return "Hello "+candidate+" "+organizationName+" organization's "+examName+" exam will be held between "+startDate.substring(11)+"-"+endDate.substring(11)+"\n"
                +"Exam will be "+duration+" minutes \nGood Luck";
    }
}
