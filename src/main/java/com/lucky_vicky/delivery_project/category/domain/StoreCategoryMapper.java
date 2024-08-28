package com.lucky_vicky.delivery_project.category.domain;

import com.lucky_vicky.delivery_project.store.domain.Store;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Table(name = "p_store_category_mapper")
public class StoreCategoryMapper {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    /* -------------- Mapping -------------- */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="store_id", nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id", nullable = false)
    private StoreCategory storeCategory;

    /* -------------- Constructor -------------- */
    @Builder
    public StoreCategoryMapper(Store store, StoreCategory storeCategory) {
        this.store = store;
        this.storeCategory = storeCategory;
    }

    /* -------------- Methods -------------- */
}
