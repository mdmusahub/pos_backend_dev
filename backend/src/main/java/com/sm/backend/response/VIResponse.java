package com.sm.backend.response;

import lombok.Data;

@Data
public class VIResponse {
    private ProductVariantResponse variantResponse;
    private ProductInventoryResponse inventoryResponse;

    public VIResponse(ProductVariantResponse variantResponse, ProductInventoryResponse inventoryResponse) {
        this.variantResponse = variantResponse;
        this.inventoryResponse = inventoryResponse;
    }
}
