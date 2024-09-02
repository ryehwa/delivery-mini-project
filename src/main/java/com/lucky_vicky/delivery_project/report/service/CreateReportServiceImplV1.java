package com.lucky_vicky.delivery_project.report.service;

import com.lucky_vicky.delivery_project.global.exception.BusinessLogicException;
import com.lucky_vicky.delivery_project.global.exception.ExceptionCode;
import com.lucky_vicky.delivery_project.report.domain.Report;
import com.lucky_vicky.delivery_project.report.domain.type.EReportTargetType;
import com.lucky_vicky.delivery_project.report.dto.CreateReplyRequestDto;
import com.lucky_vicky.delivery_project.report.dto.CreateReportRequestDto;
import com.lucky_vicky.delivery_project.report.repository.ReportRepository;
import com.lucky_vicky.delivery_project.report.usecase.CreateReportUseCase;
import com.lucky_vicky.delivery_project.user.domain.User;
import com.lucky_vicky.delivery_project.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateReportServiceImplV1 implements CreateReportUseCase {

    private final UserRepository userRepository;
    private final ReportRepository reportRepository;

    @Override
    @Transactional
    public void createReport(UUID userId, CreateReportRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        Report newReport = Report.builder()
                .title(requestDto.title())
                .content(requestDto.content())
                .targetId(requestDto.targetId())
                .user(user)
                .targetType(EReportTargetType.valueOf(requestDto.targetType()))
                .build();
        reportRepository.save(newReport);
    }

    @Override
    @Transactional
    public void replyReport(UUID reportId, CreateReplyRequestDto requestDto) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.REPORT_NOT_FOUND));
        report.setReply(requestDto.content());
    }
}
