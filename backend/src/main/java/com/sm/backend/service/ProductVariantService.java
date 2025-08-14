package com.sm.backend.service;

import com.sm.backend.request.ProductVariantRequest;
import com.sm.backend.response.ProductResponse;
import com.sm.backend.response.ProductVariantResponse;

import java.util.List;

public interface   ProductVariantService {
    void createVariant(ProductVariantRequest request);

    List<ProductVariantResponse> getAll();

    ProductVariantResponse getById(Long variantId);

    Object update(ProductVariantRequest request, Long variantId);

    void delete(Long variantId);

    List<ProductVariantResponse> findVariantByProductId(Long id);
}