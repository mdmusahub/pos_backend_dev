package com.sm.backend.request;



import com.sm.backend.request.productUpdateReq.ProductUpdateRequest;
import com.sm.backend.request.productUpdateReq.VariantUpdateRequest;
import com.sm.backend.util.RequestType;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data

public class ProductRequest {
    private Long productId;
    private String productName;
    private String sku;
    private Long categoryId;
    private String description;
    private List<ProductVariantRequest> variantRequests = new ArrayList<>();
    private List<VariantUpdateRequest> variantUpdateRequests = new ArrayList<>();
    private RequestType requestType;
}
