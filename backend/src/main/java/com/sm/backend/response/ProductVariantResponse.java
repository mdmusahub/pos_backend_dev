package com.sm.backend.response;

import com.sm.backend.model.Product;
import com.sm.backend.model.ProductVariant;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductVariantResponse {
    private Long productVariantId;
    private String variantName;
    private String variantValue;
    private Double price;
    private Long productId;
    private String productName;
    private Boolean refundable;
    public ProductVariantResponse(ProductVariant productVariant) {
        this.productVariantId = productVariant.getId();
        this.variantName = productVariant.getVariantName();
        this.variantValue = productVariant.getVariantValue();
        this.price = productVariant.getPrice();
        this.productId = productVariant.getProduct().getId();
        this.productName = productVariant.getProduct().getProductName();
        this.refundable = productVariant.getRefundable();
    }

}

