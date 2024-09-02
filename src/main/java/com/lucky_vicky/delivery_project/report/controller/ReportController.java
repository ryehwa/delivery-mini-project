package com.lucky_vicky.delivery_project.report.controller;

import com.lucky_vicky.delivery_project.global.annotation.UserId;
import com.lucky_vicky.delivery_project.report.dto.CreateReportRequestDto;
import com.lucky_vicky.delivery_project.report.dto.UpdateReportRequestDto;
import com.lucky_vicky.delivery_project.report.usecase.CreateReportUseCase;
import com.lucky_vicky.delivery_project.report.usecase.DeleteReportUseCase;
import com.lucky_vicky.delivery_project.report.usecase.ReadReportUseCase;
import com.lucky_vicky.delivery_project.report.usecase.UpdateReportUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportController {
    private final CreateReportUseCase createReportUseCase;
    private final DeleteReportUseCase deleteReportUseCase;
    private final ReadReportUseCase readReportUseCase;
    private final UpdateReportUseCase updateReportUseCase;

    /**
     * 자신의 신고 목록 조회
     * STORE, CUSTOMER
     */
    @GetMapping("")
    public ResponseEntity<?> readReportList(
            @UserId UUID userId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
            @RequestParam(value = "orderBy", defaultValue = "DESC") String orderBy

    ) {
        return ResponseEntity.ok(readReportUseCase.readReports(userId, page, size, sortBy, orderBy));
    }

    /**
     * 자신의 신고 상세 조회
     * STORE, CUSTOMER
     */
    @GetMapping("/{reportId}")
    public ResponseEntity<?> readReportDetail(
            @UserId UUID userId,
            @PathVariable UUID reportId
    ) {
        return ResponseEntity.ok(readReportUseCase.readReport(userId, reportId));
    }

    /**
     * 신고하기
     * STORE, CUSTOMER
     */
    @PostMapping("")
    public ResponseEntity<?> createReport(
            @UserId UUID userId,
            @RequestBody CreateReportRequestDto createReportRequestDto
    ) {
        createReportUseCase.createReport(userId, createReportRequestDto);
        return ResponseEntity.ok().build();
    }

    /**
     * 자신의 신고 수정
     * STORE, CUSTOMER
     */
    @PutMapping("/{reportId}")
    public ResponseEntity<?> updateReport(
            @UserId UUID userId,
            @PathVariable UUID reportId,
            @RequestBody UpdateReportRequestDto updateReportRequestDto
    ) {
        updateReportUseCase.updateReport(userId, reportId, updateReportRequestDto);
        return ResponseEntity.ok().build();
    }

    /**
     * 자신의 신고 삭제
     * STORE, CUSTOMER
     */
    @DeleteMapping("/{reportId}")
    public ResponseEntity<?> deleteReport(
            @UserId UUID userId,
            @PathVariable UUID reportId
    ) {
        deleteReportUseCase.deleteReportByUser(userId, reportId);
        return ResponseEntity.noContent().build();
    }

}
