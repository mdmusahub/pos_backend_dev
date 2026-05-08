package com.sm.backend.mapperImpl;

import com.sm.backend.mapper.ProductMapper;
import com.sm.backend.model.Product;
import com.sm.backend.response.ProductResponse;
import com.sm.backend.response.productDetailsResponses.ProductVariantInventoryResponse;
import com.sm.backend.response.productDetailsResponses.VariantInventoryResponse;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class ProductMapperImpl implements ProductMapper {
    @Override
    public ProductResponse toProductResponse(Product product) {

        return ProductResponse.builder()
                .productId(product.getId())
                .productName(product.getProductName())

                .sku(product.getSku())
                .description(product.getDescription())
                .category(product.getCategory())
                .build();
    }

    @Override
    public List<ProductResponse> toProductResponseList(List<Product> products) {
        return products.stream()
                .map(this::toProductResponse)
                .toList();
    }

    @Override
    public ProductVariantInventoryResponse
    toProductVariantInventoryResponse(Product product,
                                      List<VariantInventoryResponse>
                                              variantInventoryResponses) {
        return ProductVariantInventoryResponse.builder()
                .productId(product.getId())
                .productName(product.getProductName())
                .sku(product.getSku())
                .description(product.getDescription())
                .category(product.getCategory())
                .variantInventoryResponses(variantInventoryResponses)
                .build();
    }
}
