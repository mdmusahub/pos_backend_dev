package com.sm.backend.service;

import com.sm.backend.request.ProductInventoryRequest;

public interface ProductInventoryService {
    void register(ProductInventoryRequest request);

    Object getAll(Integer pageNumber, Integer pageSize, String sortby, String sortDir);

    Object update(ProductInventoryRequest request, Long inventoryId);

    Object findById(Long inventoryId);

    void delete(Long inventoryId);
}
