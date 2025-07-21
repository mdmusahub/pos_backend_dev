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
    private Long quantity;
    private String location;
    private LocalDateTime lastUpdated;
    private Long productId;
    private String productName;
    private Long variantId ;
    private String variantName;
    private String variantValue;
    public ProductInventoryResponse(ProductInventory inventory) {
        this.inventoryId = inventory.getId();
        this.quantity = inventory.getQuantity();
        this.location = inventory.getLocation();
        this.lastUpdated = inventory.getLastUpdated();
        this.productId = inventory.getProduct().getId();
        this.productName = inventory.getProduct().getProductName();
        this.variantId = inventory.getProductVariant().getId();
        this.variantName = inventory.getProductVariant().getVariantName();
        this.variantValue = inventory.getProductVariant().getVariantValue();
    }


}
