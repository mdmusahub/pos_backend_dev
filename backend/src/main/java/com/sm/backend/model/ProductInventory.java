package com.sm.backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class ProductInventory {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long inventoryId;
private Long quantity;
private String location;
private LocalDateTime lastUpdated;
@ManyToOne
private Product product;
@ManyToOne
private ProductVariant productVariant;


}
