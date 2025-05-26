package com.sm.backend.service;

import com.sm.backend.request.ProductVariantRequest;

public interface ProductVariantService {
    void addVariant(ProductVariantRequest request);

    Object getAllVariants();

    Object GetById(Long id);

    Object updateById(Long id, ProductVariantRequest request);

    void delete(java.lang.Long id);
}
