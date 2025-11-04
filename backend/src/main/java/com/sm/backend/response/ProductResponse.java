package com.sm.backend.response;


import com.sm.backend.model.Category;
import com.sm.backend.model.Product;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
public class ProductResponse {
    private Long productId;
    private String productName;
    private String sku;
    private String description;
    private LocalDateTime createdAt;
    private Category category;
    private LocalDateTime updatedAt;

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
