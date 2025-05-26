package com.sm.backend.request;

import com.sm.backend.model.Product;
import lombok.Data;

@Data
public class ProductVariantRequest {
    private Long productId;
    private String variantName;
    private String variantValue;
    private Double price;
}
