package com.lucky_vicky.delivery_project.payment.application.service;

import com.lucky_vicky.delivery_project.payment.application.dto.PaymentListDTO;
import com.lucky_vicky.delivery_project.payment.application.dto.PaymentRequestDTO;
import com.lucky_vicky.delivery_project.payment.application.dto.PaymentResponseDTO;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface PaymentService {

    // findAllByUserId - getPaymentList
    Page<PaymentListDTO> getPaymentList(UUID userId, int page, int size, String sortBy, boolean orderBy);

    // Create Payment
    PaymentResponseDTO createPayment(PaymentRequestDTO paymentRequestDTO);

    // findByPaymentId
    PaymentResponseDTO findByPaymentId(UUID paymentId);
}
