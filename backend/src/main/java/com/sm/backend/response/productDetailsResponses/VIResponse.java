package com.sm.backend.response.productDetailsResponses;

import com.sm.backend.response.ProductInventoryResponse;
import com.sm.backend.response.ProductVariantResponse;
import lombok.Data;

@Data
public class VIResponse {
    private VariantResponse variantResponse;
    private InventoryResponse inventoryResponse;

    public VIResponse(VariantResponse variantResponse, InventoryResponse inventoryResponse) {
        this.variantResponse = variantResponse;
        this.inventoryResponse = inventoryResponse;
    }
}
