package com.lucky_vicky.delivery_project.order.application.dto;

import com.lucky_vicky.delivery_project.order.domain.entity.Order;
import com.lucky_vicky.delivery_project.order.domain.entity.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderListDTO {

    private UUID orderId;
    private String storeName;
    private OrderStatusEnum status;
    private boolean isOnline;

    public static OrderListDTO toListDTO(Order order){

        return OrderListDTO.builder()
                .orderId(order.getId())
                .storeName(order.getStore().getName())
                .status(order.getStatus())
                .isOnline(order.isOnline())
                .build();
    }
}
