package com.lucky_vicky.delivery_project.payment.application.dto;

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
public class PaymentRequestDTO {

    private UUID orderId;
    private UUID userId;
    private int totalPrice;
    private PaymentStatusEnum status = PaymentStatusEnum.COMPLETED;
    private PaymentMethodEnum paymentMethod;
}
