package com.sm.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


@Data
@Entity
public class Product {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private String sku;
    private Double productPrice;
    private String discription;
    @ManyToOne
    private Category category;
    @DateTimeFormat
    @CreationTimestamp
    private LocalDateTime createdAt;
    @DateTimeFormat
    @CreationTimestamp
    private LocalDateTime updatedAt;
}
