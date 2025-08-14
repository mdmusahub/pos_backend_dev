package com.sm.backend.request;

import com.sm.backend.model.Category;
import com.sm.backend.util.TaxCategory;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaxRequest {

    private TaxCategory category;
    private Long productVariantPrice;
}
