package com.sm.backend.request;

import com.sm.backend.model.Category;
import com.sm.backend.util.RequestType;
import com.sm.backend.util.TaxCategory;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaxRequest {
    private TaxCategory category;
    private Long productVariantPrice;
    private Long taxId;
    private RequestType requestType;
    private Long variantId;

}
