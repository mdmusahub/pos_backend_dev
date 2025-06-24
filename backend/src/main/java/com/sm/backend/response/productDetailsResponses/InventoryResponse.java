package com.sm.backend.response.productDetailsResponses;

import com.sm.backend.model.Product;
import com.sm.backend.model.ProductInventory;
import com.sm.backend.model.ProductVariant;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InventoryResponse {
    private Long inventoryId;
    private Long quantity;
    private String location;
    private LocalDateTime lastUpdated;
    public InventoryResponse(ProductInventory inventory) {
        this.inventoryId = inventory.getInventoryId();
        this.quantity = inventory.getQuantity();
        this.location = inventory.getLocation();
        this.lastUpdated = inventory.getLastUpdated();
    }
}
