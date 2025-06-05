package com.sm.backend.service;

import com.sm.backend.request.ProductVariantRequest;

public interface ProductVariantService {
    void createVariant(ProductVariantRequest request);

    Object getAll(Integer pageNumber, Integer pageSize, String sortby, String sortDir);

    Object getById(Long variantId);

    Object update(ProductVariantRequest request, Long variantId);

    void delete(Long variantId);
}