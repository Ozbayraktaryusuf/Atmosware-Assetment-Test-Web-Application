package com.atmosware.belatrix.managmentService.grpc.client.abstracts;

import com.atmosware.belatrix.managmentService.business.dto.dtos.MailInfoDto;

public interface ExamClientService {
     MailInfoDto getExamForMail(Long examId);
}
