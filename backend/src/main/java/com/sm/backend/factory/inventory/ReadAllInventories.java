package com.sm.backend.factory.inventory;

import com.sm.backend.model.ProductInventory;
import com.sm.backend.repository.ProductInventoryRepository;
import com.sm.backend.request.ProductInventoryRequest;
import com.sm.backend.response.ProductInventoryResponse;
import com.sm.backend.util.RequestType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadAllInventories implements InventoryOperation{

    private final ProductInventoryRepository inventoryRepository;
    @Override
    public RequestType getRequestType() {
        return RequestType.READ_ALL;
    }

    @Override
    public ProductInventoryResponse execute(ProductInventoryRequest request) {
        List<ProductInventory> all = inventoryRepository.findAll();
        return ProductInventoryResponse.builder()
                .inventories(all.stream().map(
                        ProductInventoryResponse::new).toList())
                .build();
    }
}
