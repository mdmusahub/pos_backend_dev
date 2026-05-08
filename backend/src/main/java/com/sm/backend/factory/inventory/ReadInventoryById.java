package com.sm.backend.factory.inventory;

import com.sm.backend.repository.ProductInventoryRepository;
import com.sm.backend.request.ProductInventoryRequest;
import com.sm.backend.response.ProductInventoryResponse;
import com.sm.backend.util.RequestType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReadInventoryById implements InventoryOperation {

    private final ProductInventoryRepository inventoryRepository;

    @Override
    public RequestType getRequestType() {
        return RequestType.READ_BY_ID;
    }

    @Override
    public ProductInventoryResponse execute(ProductInventoryRequest request) {
        return new ProductInventoryResponse(inventoryRepository.findById(
                request.getInventoryId())
                .orElseThrow(()->new RuntimeException("invalid inventory ID")));
    }
}
