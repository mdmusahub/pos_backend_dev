package com.sm.backend.model;


import com.sm.backend.util.ReturnReason;
import com.sm.backend.util.ReturnStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "return_order_item")
@Data
@NoArgsConstructor
public class ReturnOrderItem{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Order order;
    @OneToOne(fetch = FetchType.EAGER)
    private OrderItem orderItem;
    @ManyToOne
    private Product product;
    @ManyToOne
    private ProductVariant productVariant;
    @Enumerated (EnumType.STRING)
    private ReturnStatus returnStatus;
    @Enumerated (EnumType.STRING)
    private ReturnReason returnReason;
    private Long returnQuantity;
    private Double unitPrice;
    private Double refundAmount;

}
