package com.atmosware.belatrix.managmentService.grpc.clients.abstracts;

import com.atmosware.belatrix.managmentService.business.dto.dtos.MailInfoDto;

public interface ExamClientService {
    public MailInfoDto getExamForMail(Long examId);
}
