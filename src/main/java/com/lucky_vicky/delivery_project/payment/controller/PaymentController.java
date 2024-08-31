package com.lucky_vicky.delivery_project.payment.controller;

import com.lucky_vicky.delivery_project.payment.application.dto.PaymentListDTO;
import com.lucky_vicky.delivery_project.payment.application.dto.PaymentRequestDTO;
import com.lucky_vicky.delivery_project.payment.application.dto.PaymentResponseDTO;
import com.lucky_vicky.delivery_project.payment.application.service.PaymentService;
import com.lucky_vicky.delivery_project.user.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Log4j2(topic = "Payment Controller")
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    // 결제 목록 조회
    @GetMapping("")
    public Page<PaymentListDTO> getPaymentList(@RequestParam(value = "page", defaultValue = "1") int page,
                                               @RequestParam(value = "size", defaultValue = "10") int size,
                                               @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
                                               @RequestParam(value = "desc", defaultValue = "true") boolean orderBy,
                                               @AuthenticationPrincipal UserPrincipal userPrincipal) {

        log.info("PaymentController : GET getPaymentList");

        UUID userId = userPrincipal.getId();
        return paymentService.getPaymentList(userId, page-1, size, sortBy, orderBy);
    }

    // 결제 내역 생성
    @PostMapping("")
    public ResponseEntity<PaymentResponseDTO> createPayment(@RequestBody PaymentRequestDTO paymentRequestDTO, @AuthenticationPrincipal UserPrincipal userPrincipal) {

        log.info("PaymentController : POST createPayment");

        UUID userId = userPrincipal.getId();
        paymentRequestDTO.setUserId(userId);

        PaymentResponseDTO dto = paymentService.createPayment(paymentRequestDTO);
        return ResponseEntity.ok(dto);
    }

    // 결제 내역 조회
    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponseDTO> findById(@PathVariable UUID paymentId) {

        log.info("PaymentController : GET findById");

        PaymentResponseDTO dto = paymentService.findByPaymentId(paymentId);
        return ResponseEntity.ok(dto);
    }
}
