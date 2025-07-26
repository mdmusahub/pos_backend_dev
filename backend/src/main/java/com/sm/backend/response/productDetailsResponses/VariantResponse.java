package com.sm.backend.response.productDetailsResponses;

import com.sm.backend.model.Product;
import com.sm.backend.model.ProductVariant;
import lombok.Data;

@Data
public class VariantResponse {
    private Long productVariantId;
    private String variantName;
    private String variantValue;
    private Double price;
    public VariantResponse(ProductVariant productVariant) {
        this.productVariantId = productVariant.getProductVariantId();
        this.variantName = productVariant.getVariantName();
        this.variantValue = productVariant.getVariantValue();
        this.price = productVariant.getPrice();
    }
}
