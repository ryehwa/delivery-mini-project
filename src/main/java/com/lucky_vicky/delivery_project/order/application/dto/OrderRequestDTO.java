package com.lucky_vicky.delivery_project.order.application.dto;


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
public class OrderRequestDTO {

    private Long userId;
    private UUID storeId;
    private List<OrderProductDTO> orderProductList = new ArrayList<>();
    private boolean isOnline;

    // 배송지
    private String address;
    private String recipientName;
    private boolean isDefault;
}
