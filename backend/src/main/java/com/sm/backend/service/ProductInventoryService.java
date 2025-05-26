package com.sm.backend.service;

import com.sm.backend.request.ProductInventoryRequest;

public interface ProductInventoryService {
    void addInventory(ProductInventoryRequest request);

    Object getAll();

    Object getById(Long id);

    Object updateById(Long id,ProductInventoryRequest request);

    void deleteById(Long id);
}
