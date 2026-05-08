package com.sm.backend.request;

import com.sm.backend.model.Product;
import com.sm.backend.util.RequestType;
import lombok.Data;

import java.util.List;

@Data
public class ProductVariantRequest {
    private Long productId;
    private String variantName;
    private String variantValue;
    private Double price;
    private ProductInventoryRequest inventoryRequest;
    private Boolean refundable;
    private Long variantId;
    private RequestType requestType;

}
