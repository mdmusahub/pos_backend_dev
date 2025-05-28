package com.sm.backend.response;

import com.sm.backend.model.Product;
import com.sm.backend.model.ProductInventory;
import com.sm.backend.model.ProductVariant;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Data
@NoArgsConstructor
public class ProductInventoryResponse {
    private Long inventoryId;
    private Integer quantity;
    private String location;
    private LocalDateTime lastUpdated;
    private Product product;
    private ProductVariant productVariant ;
    public ProductInventoryResponse(ProductInventory inventory) {
        this.inventoryId = inventory.getInventoryId();
        this.quantity = inventory.getQuantity();
        this.location = inventory.getLocation();
        this.lastUpdated = inventory.getLastUpdated();
        this.product = inventory.getProduct();
        this.productVariant = inventory.getProductVariant();
    }


}
