package com.sm.backend.response.productDetailsResponses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sm.backend.model.Product;
import com.sm.backend.model.ProductInventory;
import com.sm.backend.model.ProductVariant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InventoryResponse {
    private Long inventoryId;
    private Long quantity;
    private String location;
    private LocalDateTime lastUpdated;
    private String message;
    private ProductVariantInventoryResponse variantInventoryResponse;

    public InventoryResponse(ProductInventory inventory) {
        this.inventoryId = inventory.getId();
        this.quantity = inventory.getQuantity();
        this.location = inventory.getLocation();
        this.lastUpdated = inventory.getLastUpdated();
    }
}
