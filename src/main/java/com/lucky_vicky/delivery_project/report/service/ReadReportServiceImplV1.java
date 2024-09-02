package com.lucky_vicky.delivery_project.report.service;

import com.lucky_vicky.delivery_project.global.exception.BusinessLogicException;
import com.lucky_vicky.delivery_project.global.exception.ExceptionCode;
import com.lucky_vicky.delivery_project.report.domain.Report;
import com.lucky_vicky.delivery_project.report.dto.ReportDetailResponseDto;
import com.lucky_vicky.delivery_project.report.dto.ReportSummaryResponseDto;
import com.lucky_vicky.delivery_project.report.repository.ReportRepository;
import com.lucky_vicky.delivery_project.report.usecase.ReadReportUseCase;
import com.lucky_vicky.delivery_project.user.domain.User;
import com.lucky_vicky.delivery_project.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadReportServiceImplV1 implements ReadReportUseCase {
    private final UserRepository userRepository;
    private final ReportRepository reportRepository;

    @Override
    public ReportDetailResponseDto readReport(UUID userId, UUID reportId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.REPORT_NOT_FOUND));
        return ReportDetailResponseDto.fromEntity(report);
    }

    @Override
    public Page<ReportSummaryResponseDto> readReports(UUID userId, int page, int size, String orderBy, String sortBy) {
        Sort.Direction direction = orderBy.equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        Page<Report> reportPage = reportRepository.findAllByUserAndIsDeletedFalse(user, pageable);

        return reportPage.map(ReportSummaryResponseDto::fromEntity);
    }

    @Override
    public Page<ReportSummaryResponseDto> readAllReports(int page, int size, String orderBy, String sortBy) {
        Sort.Direction direction = orderBy.equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<Report> reportPage = reportRepository.findAllByIsDeletedFalse(pageable);

        return reportPage.map(ReportSummaryResponseDto::fromEntity);
    }

    @Override
    public Page<ReportSummaryResponseDto> searchReports(int page, int size, String text) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Report> reportPage = reportRepository.findByIsDeletedFalseAndTitleContaining(text, pageable);
        return reportPage.map(ReportSummaryResponseDto::fromEntity);
    }
}
