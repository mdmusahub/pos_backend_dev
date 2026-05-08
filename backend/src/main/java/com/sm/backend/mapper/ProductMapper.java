package com.sm.backend.mapper;

import com.sm.backend.model.Product;
import com.sm.backend.response.ProductInventoryResponse;
import com.sm.backend.response.ProductResponse;
import com.sm.backend.response.productDetailsResponses.ProductVariantInventoryResponse;
import com.sm.backend.response.productDetailsResponses.VariantInventoryResponse;

import java.util.List;

public interface ProductMapper {

    ProductResponse toProductResponse(Product product);
    List<ProductResponse> toProductResponseList(List<Product> products);
    ProductVariantInventoryResponse
    toProductVariantInventoryResponse(Product product, List<
            VariantInventoryResponse> variantInventoryResponses);
}
