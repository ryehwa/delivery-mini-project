package com.lucky_vicky.delivery_project.product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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


    @PrePersist
    protected void createUUID(){
        if(id == null) id = UUID.randomUUID();
    }

}
