package com.sm.backend.request;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class ProductInventoryRequest {
    private Long quantity;
    private String location;
    private LocalDateTime lastUpdated;
//    private Long productId;
    private Long variantId;
}
