package com.lucky_vicky.delivery_project.report.service;

import com.lucky_vicky.delivery_project.global.exception.BusinessLogicException;
import com.lucky_vicky.delivery_project.global.exception.ExceptionCode;
import com.lucky_vicky.delivery_project.report.domain.Report;
import com.lucky_vicky.delivery_project.report.domain.type.EReportTargetType;
import com.lucky_vicky.delivery_project.report.dto.UpdateReportRequestDto;
import com.lucky_vicky.delivery_project.report.repository.ReportRepository;
import com.lucky_vicky.delivery_project.report.usecase.UpdateReportUseCase;
import com.lucky_vicky.delivery_project.user.domain.User;
import com.lucky_vicky.delivery_project.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateReportServiceImplV1 implements UpdateReportUseCase {

    private final UserRepository userRepository;
    private final ReportRepository reportRepository;

    @Override
    @Transactional
    public void updateReport(UUID userId, UUID reportId, UpdateReportRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.REPORT_NOT_FOUND));
        report.update(
                requestDto.title(),
                requestDto.content(),
                EReportTargetType.valueOf(requestDto.targetType()),
                requestDto.targetId()
        );
    }
}
