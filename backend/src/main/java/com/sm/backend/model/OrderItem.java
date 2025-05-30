package com.sm.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;
    @ManyToOne
    private Order orderId;
    @OneToOne
    private Product productId;
    @OneToOne
    private ProductVariant variantId;
    private Integer quantity;
    private Double unitPrice;
    private Double totalPrice;
}
