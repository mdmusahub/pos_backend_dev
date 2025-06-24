package com.sm.backend.request.productUpdateReq;

import lombok.Data;

import java.util.List;

@Data
public class ProdRequest {
    private String productName;
    private String sku;
    private String description;
    private Long categoryId;
    List<VarRequest> variant;
}
