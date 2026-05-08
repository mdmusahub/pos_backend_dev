package com.sm.backend.factory.inventory;

import com.sm.backend.request.ProductInventoryRequest;
import com.sm.backend.response.ProductInventoryResponse;
import com.sm.backend.util.RequestType;

public interface InventoryOperation {
    RequestType getRequestType();

    ProductInventoryResponse execute(ProductInventoryRequest request);
}
