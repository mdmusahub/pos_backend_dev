package com.sm.backend.request.productUpdateReq;

import lombok.Data;

@Data
public class VariantUpdateRequest {
    private Long variantId;
    private String variantName;
    private String variantValue;
    private Double variantPrice;
    //these two fields are for updating inventory;
    private Long quantity;
    private String location;
}
