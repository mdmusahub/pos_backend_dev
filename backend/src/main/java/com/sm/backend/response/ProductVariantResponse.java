package com.sm.backend.response;

import com.sm.backend.model.Product;
import com.sm.backend.model.ProductVariant;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductVariantResponse {
    private Long productVariantId;
    private Product product;
    private String variantName;
    private String variantValue;
    private Double price;
    public ProductVariantResponse(ProductVariant productVariant) {
        this.productVariantId = productVariant.getProductVariantId();
        this.product = productVariant.getProduct()  ;
        this.variantName = productVariant.getVariantName();
        this.variantValue = productVariant.getVariantValue();
        this.price = productVariant.getPrice();
    }

}

