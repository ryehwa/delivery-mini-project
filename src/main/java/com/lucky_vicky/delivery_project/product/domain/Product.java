package com.lucky_vicky.delivery_project.product.domain;

import com.lucky_vicky.delivery_project.product.dto.ProductRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.UUID;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private boolean isHidden;

    @Column(nullable = false)
    private boolean isDeleted;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "store_id", nullable = false)
//    private Store store;

    @PrePersist
    protected void createUUID(){
        if(id == null) id = UUID.randomUUID();
    }

    public Product(ProductRequestDto productRequestDto) {
        this.name = productRequestDto.getName();
        this.description = productRequestDto.getDescription();
        this.price = productRequestDto.getPrice();
        this.isHidden = productRequestDto.isHidden();
    }

    public void update(ProductRequestDto productRequestDto) {
        this.name = productRequestDto.getName();
        this.description = productRequestDto.getDescription();
        this.price = productRequestDto.getPrice();
        this.isHidden = productRequestDto.isHidden();

    }
}