package com.lucky_vicky.delivery_project.order.application.dto;

import com.lucky_vicky.delivery_project.order.domain.entity.Order;
import com.lucky_vicky.delivery_project.order.domain.enums.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderUpdateStatusDTO {

    private UUID orderId;
    private OrderStatusEnum status;

    public static OrderUpdateStatusDTO toStatusDTO(Order order){
        return OrderUpdateStatusDTO.builder()
                .orderId(order.getOrderId())
                .status(order.getStatus())
                .build();
    }
}
