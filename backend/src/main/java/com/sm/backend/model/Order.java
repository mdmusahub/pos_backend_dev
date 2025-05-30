package com.sm.backend.model;

import com.sm.backend.util.OrderStatus;
import com.sm.backend.util.PaymentMode;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private String phoneNo;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private Double discount;
    private Double tax;
    private Double totalAmount;
    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode;
    private Double onlineAmount;
    private Double cashAmount;
    private LocalDateTime orderDate;
    private LocalDateTime updatedAt;

}
