package com.sm.backend.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sm.backend.model.Product;
import com.sm.backend.model.ProductInventory;
import com.sm.backend.model.ProductVariant;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductInventoryResponse {
    private Long inventoryId;
    private Long quantity;
    private String location;
    private LocalDateTime lastUpdated;
    private Product product;
    private ProductVariant productVariant;
    private String message;
    private List<ProductInventoryResponse> inventories;

    public ProductInventoryResponse(ProductInventory inventory) {
        this.inventoryId = inventory.getId();
        this.quantity = inventory.getQuantity();
        this.location = inventory.getLocation();
        this.lastUpdated = inventory.getLastUpdated();
        this.product = inventory.getProduct();
        this.productVariant = inventory.getProductVariant();
    }


}
