package com.sm.backend.factory.inventory;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.model.ProductInventory;
import com.sm.backend.model.ProductVariant;
import com.sm.backend.repository.ProductInventoryRepository;
import com.sm.backend.repository.ProductVariantRepository;
import com.sm.backend.request.ProductInventoryRequest;
import com.sm.backend.response.ProductInventoryResponse;
import com.sm.backend.response.productDetailsResponses.InventoryResponse;
import com.sm.backend.util.RequestType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateInventory implements InventoryOperation{

    private final ProductInventoryRepository inventoryRepository;
    private final ProductVariantRepository variantRepository;

    @Override
    public RequestType getRequestType() {
        return RequestType.CREATE;
    }

    @Override
    public ProductInventoryResponse execute(ProductInventoryRequest request) {
        ProductInventory inventory = new ProductInventory();
        inventory.setLocation(request.getLocation());
        inventory.setQuantity(request.getQuantity());
//        inventory.setLastUpdated(request.getLastUpdated());
        ProductVariant variant = variantRepository.findById(
                request.getVariantId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "invalid variant id"));
        inventory.setProductVariant(variant);
        inventory.setProduct(variant.getProduct());
        inventoryRepository.save(inventory);

        return ProductInventoryResponse.builder()
                .message("Inventory created successfully")
                .build();
    }
}
