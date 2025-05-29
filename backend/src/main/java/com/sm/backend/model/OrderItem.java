package com.sm.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class OrderItem {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;
@ManyToOne
private Order order;
@ManyToOne
    private Product product;
@ManyToOne
    private ProductVariant productVariant;
private Long quantity;
private Double unitPrice;
private Double totalPrice;
}
