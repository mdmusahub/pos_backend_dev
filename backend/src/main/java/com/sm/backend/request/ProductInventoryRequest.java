package com.sm.backend.request;

import com.sm.backend.model.Product;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductInventoryRequest {
    private Integer quantity;
    private String location;
    private LocalDateTime lastUpdated;
    private Long productId;
}
