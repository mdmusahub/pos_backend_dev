package com.sm.backend.response.productDetailsResponses;


import com.sm.backend.model.Category;
import com.sm.backend.model.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductVariantInventoryResponse {
    private Long productId;
    private String productName;
    private String sku;
    private String description;
    private LocalDateTime createdAt;
    private Category category;
    private LocalDateTime updatedAt;
    private List<VariantInventoryResponse> variantInventoryResponses;


    public ProductVariantInventoryResponse(Product product, List<VariantInventoryResponse> variantInventoryResponses) {
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.sku = product.getSku();
        this.description = product.getDescription();
        this.createdAt = product.getCreatedAt();
        this.category = product.getCategory();
        this.updatedAt = product.getUpdatedAt();
        this.variantInventoryResponses = variantInventoryResponses;
    }


}
