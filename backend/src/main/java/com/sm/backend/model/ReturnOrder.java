package com.sm.backend.model;

import com.sm.backend.util.ReturnReason;
import com.sm.backend.util.ReturnStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "return_order")
@Data
public class ReturnOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Order orderId;
    private Long returnQuantity;
    @Enumerated (EnumType.STRING)
    private ReturnReason returnReason;
    @Enumerated (EnumType.STRING)
    private ReturnStatus returnStatus;
    private Double refundAmount;
    private LocalDateTime returnDate;

}
