package com.sm.backend.factory.productVariant;

import com.sm.backend.exceptionalHandling.ProductCanNotBeDeletedException;
import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.model.ProductInventory;
import com.sm.backend.model.ProductVariant;
import com.sm.backend.repository.OrderItemRepository;
import com.sm.backend.repository.ProductInventoryRepository;
import com.sm.backend.repository.ProductVariantRepository;
import com.sm.backend.request.ProductVariantRequest;
import com.sm.backend.response.ProductVariantResponse;
import com.sm.backend.util.RequestType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteVariant implements VariantOperation{

    private final ProductVariantRepository repository;

    private final ProductInventoryRepository inventoryRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public RequestType getRequestType() {
        return RequestType.DELETE;
    }

    @Override
    public ProductVariantResponse execute(ProductVariantRequest request) {

        ProductVariant variant =
                repository.findById(request.getVariantId()).orElseThrow(() -> new ResourceNotFoundException("invalid variant id"));
        ProductInventory inventory = inventoryRepository.findProductInventoryByProductVariant(variant);
        if (orderItemRepository.findOrderItemByProductVariant(variant).isPresent()){
            throw new ProductCanNotBeDeletedException("this variant cannot be deleted since it already exists in an order item");
        }
        else {
            inventoryRepository.delete(inventory);
            repository.delete(variant);
        }
        return ProductVariantResponse.builder()
                .message("Deleted variant with id " + request.getVariantId())
                .build();
    }
}
