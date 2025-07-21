package com.sm.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ProductVariant {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
@ManyToOne
@JoinColumn(name = "prodcut_id")
private Product product;
private String variantName;
private String variantValue;
private Double price;
private Boolean refundable;
}
