package com.sm.backend.service;

import com.sm.backend.request.ProductInventoryRequest;
import com.sm.backend.response.ProductInventoryResponse;

import java.util.List;

public interface ProductInventoryService {
    void createInventory(ProductInventoryRequest request);

    List<ProductInventoryResponse> getAll(Integer pageNumber, Integer pageSize, String sortby, String sortDir);

    Object update(ProductInventoryRequest request, Long inventoryId);

    Object getById(Long inventoryId);

    void delete(Long inventoryId);
}
