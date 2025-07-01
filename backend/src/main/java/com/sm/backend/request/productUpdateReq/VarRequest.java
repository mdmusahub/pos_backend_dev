package com.sm.backend.request.productUpdateReq;

import lombok.Data;

@Data
public class VarRequest {
    private Long variantId;
    private String variantName;
    private String variantValue;
    private Double variantPrice;
    //these two field belongs to Inventory;
    private Long quantity;
    private String location;
}
