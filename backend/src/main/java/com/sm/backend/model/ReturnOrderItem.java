package com.sm.backend.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "return_order_item")
@Data
@NoArgsConstructor
public class ReturnOrderItem{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Order orderId;
    @OneToOne(fetch = FetchType.EAGER)
    private OrderItem orderItem;
    @ManyToOne
    private Product productId;
    @OneToMany
    private ProductVariant productVariantId;
    private Long returnQuantity;
    private Double unitPrice;
    private Double refundAmount;
    private Customer requestedBy;

}
