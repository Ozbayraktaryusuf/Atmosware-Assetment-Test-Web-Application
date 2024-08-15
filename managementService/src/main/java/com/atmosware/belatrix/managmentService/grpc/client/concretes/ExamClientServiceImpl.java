package com.atmosware.belatrix.managmentService.grpc.client.concretes;

import com.atmosware.belatrix.managmentService.business.dto.dtos.MailInfoDto;
import com.atmosware.belatrix.managmentService.core.exceptions.types.BusinessException;
import com.atmosware.belatrix.managmentService.core.exceptions.types.NotFoundException;
import com.atmosware.belatrix.managmentService.grpc.client.abstracts.ExamClientService;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import tr.com.atmosware.gyt.grpc.ExamServiceGrpc;
import tr.com.atmosware.gyt.grpc.GetExamRequest;
@Service
public class ExamClientServiceImpl implements ExamClientService {
    @GrpcClient("examService")
    ExamServiceGrpc.ExamServiceBlockingStub examServiceBlockingStub ;

    @Override
    public MailInfoDto getExamForMail(Long examId) {
        try {
            final var request = GetExamRequest.newBuilder().setExamId(examId).build();
            final var response = examServiceBlockingStub.getExam(request);

            MailInfoDto mailInfoDto = new MailInfoDto();
            mailInfoDto.setExamName(response.getExamName());
            mailInfoDto.setDuration(response.getDuration());
            mailInfoDto.setStartDate(response.getStartDate());
            mailInfoDto.setEndDate(response.getEndDate());

            return mailInfoDto;
        }catch (StatusRuntimeException e){
            if (e.getStatus().getCode()== Status.Code.NOT_FOUND){
                throw new NotFoundException("Soru bulunmadi.");
            }
            else{
                throw new BusinessException("An unexpected error occurred in ExamService.");
            }
        }
    }
}
