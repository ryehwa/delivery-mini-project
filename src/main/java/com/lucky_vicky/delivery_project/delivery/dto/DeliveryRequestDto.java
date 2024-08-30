package com.lucky_vicky.delivery_project.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryRequestDto {

    private String address;
    private String recipientName;
    private boolean isDefault;
    private boolean isDeleted;

}
