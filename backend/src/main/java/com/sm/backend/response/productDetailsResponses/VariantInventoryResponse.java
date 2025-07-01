package com.sm.backend.response.productDetailsResponses;

import lombok.Data;

@Data
public class VariantInventoryResponse {
    private VariantResponse variantResponse;
    private InventoryResponse inventoryResponse;

    public VariantInventoryResponse(VariantResponse variantResponse, InventoryResponse inventoryResponse) {
        this.variantResponse = variantResponse;
        this.inventoryResponse = inventoryResponse;
    }
}
