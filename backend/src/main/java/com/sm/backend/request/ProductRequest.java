package com.sm.backend.request;



import lombok.Data;


import java.time.LocalDateTime;
import java.util.List;


@Data

public class ProductRequest {
    private String productName;
    private String sku;
    private Long categoryId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String description;
    private List<ProductVariantRequest> variantRequests;
}
