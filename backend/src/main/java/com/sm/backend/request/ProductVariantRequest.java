package com.sm.backend.request;

import lombok.Data;

@Data
public class ProductVariantRequest {
    private Long productId;
    private String variantName;
    private String variantValue;
    private Double price;
}
