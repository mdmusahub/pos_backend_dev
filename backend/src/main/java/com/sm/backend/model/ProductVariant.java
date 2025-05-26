package com.sm.backend.model;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class ProductVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long variantId;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product productId;
    private String variantName;
    private String variantValue;
    private Double price;
}
