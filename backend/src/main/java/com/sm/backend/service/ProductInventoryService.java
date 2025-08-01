package com.sm.backend.service;

import com.sm.backend.request.ProductInventoryRequest;
import com.sm.backend.response.ProductInventoryResponse;

import java.util.List;

public interface ProductInventoryService {
    void createInventory(ProductInventoryRequest request);

    List<ProductInventoryResponse> getAll();

    Object update(ProductInventoryRequest request, Long inventoryId);

    ProductInventoryResponse getById(Long inventoryId);

    void delete(Long inventoryId);
}
