package com.sm.backend.factory.inventory;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.model.Product;
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
public class UpdateInventory implements InventoryOperation{

    private final ProductInventoryRepository inventoryRepository;
    private final ProductVariantRepository variantRepository;
    @Override
    public RequestType getRequestType() {
        return RequestType.UPDATE;
    }

    @Override
    public ProductInventoryResponse execute(ProductInventoryRequest request) {
        ProductInventory inventory =
                inventoryRepository.findById(request.getInventoryId()).
                        orElseThrow(() -> new ResourceNotFoundException(
                                "invalid inventory ID"));
        if (request.getQuantity()!=null){
            inventory.setQuantity(request.getQuantity());
        }
        if (request.getVariantId()!=null){
            ProductVariant variant = variantRepository.findById(
                    request.getVariantId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "invalid variant ID"));
            inventory.setProduct(variant.getProduct());
            inventory.setProductVariant(variant);
        }
        if (request.getLocation()!=null){
            inventory.setLocation(request.getLocation());
        }
//      if (request.getLastUpdated()!=null){
//          inventory.setLastUpdated(request.getLastUpdated());
//      }
        inventoryRepository.save(inventory);
        return ProductInventoryResponse.builder()
                .message("inventory updated successfully")
                .build();
    }
}
