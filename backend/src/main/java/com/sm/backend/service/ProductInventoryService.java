package com.sm.backend.service;

import com.sm.backend.request.ProductInventoryRequest;

public interface ProductInventoryService {
    void createInventory(ProductInventoryRequest request);

    Object getAll(Integer pageNumber, Integer pageSize, String sortby, String sortDir);

    Object update(ProductInventoryRequest request, Long inventoryId);

    Object getById(Long inventoryId);

    void delete(Long inventoryId);
}
