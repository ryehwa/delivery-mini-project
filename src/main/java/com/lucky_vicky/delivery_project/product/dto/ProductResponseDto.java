package com.lucky_vicky.delivery_project.product.dto;

import com.lucky_vicky.delivery_project.product.domain.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class ProductResponseDto {

    private UUID id;
    private String name;
    private String description;
    private int price;
    private boolean isHidden;

    public ProductResponseDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.isHidden = product.isHidden();
    }

}
