package com.lucky_vicky.delivery_project.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {

    private String name;
    private String description;
    private int price;
    private boolean isHidden;

}
