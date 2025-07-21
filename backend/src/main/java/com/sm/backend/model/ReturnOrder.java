package com.sm.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "return_order")
@Data
public class ReturnOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Order order;
    private Long returnQuantity;
    private Double refundAmount;
    @CreationTimestamp
    private LocalDateTime returnDate;
    @ManyToOne
    private Customer requestBy;

}
