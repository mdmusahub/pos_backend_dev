package com.sm.backend.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sm.backend.model.Product;
import com.sm.backend.model.ProductVariant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductVariantResponse {
    private Long productVariantId;
    private Product product;
    private String variantName;
    private String variantValue;
    private Boolean refundable;
    private Double price;
    private String message;
    private List<ProductVariantResponse> variants;

    public ProductVariantResponse(ProductVariant productVariant) {
        this.productVariantId = productVariant.getId();
        this.product = productVariant.getProduct()  ;
        this.variantName = productVariant.getVariantName();
        this.variantValue = productVariant.getVariantValue();
        this.refundable = productVariant.getRefundable();
        this.price = productVariant.getPrice();
    }

}

