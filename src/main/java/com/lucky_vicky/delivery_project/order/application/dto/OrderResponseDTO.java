package com.lucky_vicky.delivery_project.order.application.dto;

import com.lucky_vicky.delivery_project.order.domain.entity.Order;
import com.lucky_vicky.delivery_project.order.domain.enums.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDTO {

    private UUID orderId;
    private List<OrderProductDTO> orderProductDTOList = new ArrayList<>();

    private String address;
    private int totalPrice;
    private OrderStatusEnum status;
    private boolean isOnline;

    public static OrderResponseDTO toResponseDTO(Order order) {
        return OrderResponseDTO.builder()
                .orderId(order.getId())
                .orderProductDTOList(order.getOrderProductList().stream().map(
                        op -> OrderProductDTO.builder()
                                .productId(op.getProduct().getId())
                                .price(op.getProduct().getPrice())
                                .amount(op.getAmount())
                                .build()).toList())
                .address(order.getOrderDeliveryList().stream()
                        .findFirst()
                        .map(od -> od.getDelivery().getAddress())
                        .orElse(null))  // 기본값으로 null 설정
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus())
                .isOnline(order.isOnline())
                .build();
    }
}
