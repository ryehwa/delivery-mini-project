package com.lucky_vicky.delivery_project.category.domain;

import com.lucky_vicky.delivery_project.global.audit.AuditingEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Table(name = "p_store_category")
public class StoreCategory extends AuditingEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    /* -------------- Mapping -------------- */
    @OneToMany(mappedBy = "storeCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StoreCategoryMapper> storeCategoryMappers = new ArrayList<>();

    /* -------------- Constructor -------------- */
    @Builder
    public StoreCategory(String name) {
        this.name = name;
    }

    /* -------------- Methods -------------- */
    public void delete() {
        this.setIsDeleted(true);
    }

}
