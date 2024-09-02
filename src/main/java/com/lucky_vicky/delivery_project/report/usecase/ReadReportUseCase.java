package com.lucky_vicky.delivery_project.report.usecase;

import com.lucky_vicky.delivery_project.global.annotation.UseCase;
import com.lucky_vicky.delivery_project.report.dto.ReportDetailResponseDto;
import com.lucky_vicky.delivery_project.report.dto.ReportSummaryResponseDto;
import org.springframework.data.domain.Page;

import java.util.UUID;

@UseCase("readReportUseCase")
public interface ReadReportUseCase {
    ReportDetailResponseDto readReport(UUID userId);
    Page<ReportSummaryResponseDto> readReports(UUID userId);
    Page<ReportSummaryResponseDto> readAllReports(UUID userId, int page, int size, String orderBy, String sortBy);
    Page<ReportSummaryResponseDto> searchReports(UUID userId, int page, int size, String text);
}
