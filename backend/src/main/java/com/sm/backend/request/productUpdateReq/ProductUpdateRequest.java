package com.sm.backend.request.productUpdateReq;

import lombok.Data;

import java.util.List;

@Data
public class ProductUpdateRequest {
    private String productName;
    private String sku;
    private String description;
    private Long categoryId;
    List<VariantUpdateRequest> variant;
}
