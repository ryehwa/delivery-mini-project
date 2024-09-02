package com.lucky_vicky.delivery_project.report.service;

import com.lucky_vicky.delivery_project.global.exception.BusinessLogicException;
import com.lucky_vicky.delivery_project.global.exception.ExceptionCode;
import com.lucky_vicky.delivery_project.report.domain.Report;
import com.lucky_vicky.delivery_project.report.repository.ReportRepository;
import com.lucky_vicky.delivery_project.report.usecase.DeleteReportUseCase;
import com.lucky_vicky.delivery_project.user.domain.User;
import com.lucky_vicky.delivery_project.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteReportServiceImplV1 implements DeleteReportUseCase {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void deleteReportByAdmin(UUID reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.REPORT_NOT_FOUND));
        report.delete();
    }

    @Override
    @Transactional
    public void deleteReportByUser(UUID userId, UUID reportId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.REPORT_NOT_FOUND));
        report.delete();
    }
}
