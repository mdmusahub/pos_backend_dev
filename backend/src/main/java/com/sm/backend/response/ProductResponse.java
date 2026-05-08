package com.sm.backend.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.sm.backend.model.Category;
import com.sm.backend.model.Product;
import com.sm.backend.response.productDetailsResponses.ProductVariantInventoryResponse;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponse {
    private Long productId;
    private String productName;
    private String sku;
    private String description;
    private LocalDateTime createdAt;
    private Category category;
    private LocalDateTime updatedAt;
    private String message;
    private List<ProductResponse> products;
     public ProductResponse(Product product) {
         this.productId = product.getId();
         this.productName = product.getProductName();
         this.sku = product.getSku();
         this.description = product.getDescription();
         this.createdAt = product.getCreatedAt();
         this.category = product.getCategory();
         this.updatedAt =product.getUpdatedAt() ;
     }


}
