package com.sm.backend.service;

import com.sm.backend.request.ProductVariantRequest;

public interface ProductVariantService {
    void register(ProductVariantRequest request);

    Object getAll(Integer pageNumber, Integer pageSize, String sortby, String sortDir);

    Object findById(Long variantId);

    Object updateVariant(ProductVariantRequest request, Long variantId);

    void delete(Long variantId);
}