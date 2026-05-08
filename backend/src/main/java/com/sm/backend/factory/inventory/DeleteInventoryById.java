package com.sm.backend.factory.inventory;

import com.sm.backend.repository.ProductInventoryRepository;
import com.sm.backend.request.ProductInventoryRequest;
import com.sm.backend.response.ProductInventoryResponse;
import com.sm.backend.util.RequestType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteInventoryById implements InventoryOperation{

    private final ProductInventoryRepository inventoryRepository;
    @Override
    public RequestType getRequestType() {
        return RequestType.DELETE;
    }

    @Override
    public ProductInventoryResponse execute(ProductInventoryRequest request) {
        inventoryRepository.deleteById(request.getInventoryId());

        return ProductInventoryResponse.builder()
                .message("Inventory with ID " + request.getInventoryId() + " " +
                        "has been deleted successfully.")
                .build();
    }
}
