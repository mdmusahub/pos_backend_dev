package com.sm.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class ProductInventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryId;
    private Integer quantity;
    private String location;
    private LocalDateTime lastUpdated;
    @ManyToOne
    private Product productId;
    @ManyToMany
    private List<ProductVariant> variantId;
}
