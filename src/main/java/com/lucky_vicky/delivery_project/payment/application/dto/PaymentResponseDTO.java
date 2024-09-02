package com.lucky_vicky.delivery_project.payment.application.dto;

import com.lucky_vicky.delivery_project.payment.domain.entity.Payment;
import com.lucky_vicky.delivery_project.payment.domain.enums.PaymentMethodEnum;
import com.lucky_vicky.delivery_project.payment.domain.enums.PaymentStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponseDTO {

    private UUID paymentId;
    private UUID orderId;
    private UUID userId;
    private int totalPrice;
    private PaymentStatusEnum status;
    private PaymentMethodEnum paymentMethod;

    // Payment -> ResponseDTO
    public static PaymentResponseDTO toDTO (Payment payment){
        return PaymentResponseDTO.builder()
                .paymentId(payment.getId())
                .orderId(payment.getOrder().getId())
                .userId(payment.getUser().getId())
                .totalPrice(payment.getTotalPrice())
                .status(payment.getStatus())
                .paymentMethod(payment.getPaymentMethod())
                .build();
    }
}
