package com.sm.backend.response;

import com.sm.backend.model.Product;
import com.sm.backend.model.ProductVariant;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class ProductVariantResponse {
    private Long variantId;
    private String variantName;
    private String variantValue;
    private Double price;
    private Product productId;

    public ProductVariantResponse(ProductVariant variant) {
        this.price = variant.getPrice();
        this.variantId = variant.getVariantId();
        this.variantName = variant.getVariantName();
        this.variantValue = variant.getVariantValue();
        this.productId = variant.getProductId();
    }
}
