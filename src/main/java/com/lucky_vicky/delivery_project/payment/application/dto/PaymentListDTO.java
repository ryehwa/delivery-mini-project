package com.lucky_vicky.delivery_project.payment.application.dto;

import com.lucky_vicky.delivery_project.payment.domain.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentListDTO {

    private UUID paymentId;
    private UUID orderId;
    private Long userId;

    // Entity -> PaymentListDTO
    public static PaymentListDTO toDTO(Payment payment){
        return PaymentListDTO.builder()
                .paymentId(payment.getId())
                .orderId(payment.getOrder().getId())
                .userId(payment.getUser().getUserId())
                .build();
    }
}
