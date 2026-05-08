package com.sm.backend.factory.productVariant;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.model.Product;
import com.sm.backend.model.ProductInventory;
import com.sm.backend.model.ProductVariant;
import com.sm.backend.repository.ProductInventoryRepository;
import com.sm.backend.repository.ProductRepository;
import com.sm.backend.repository.ProductVariantRepository;
import com.sm.backend.request.ProductVariantRequest;
import com.sm.backend.response.ProductVariantResponse;
import com.sm.backend.util.RequestType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateVariant implements VariantOperation{

    private final ProductVariantRepository repository;

    private final ProductRepository productRepository;

    private final ProductInventoryRepository inventoryRepository;

    @Override
    public RequestType getRequestType() {
        return RequestType.CREATE;
    }

    @Override
    public ProductVariantResponse execute(ProductVariantRequest request) {

        ProductVariant variant = new ProductVariant();
        Product productId = productRepository.findById(request.getProductId()).orElseThrow(() -> new ResourceNotFoundException("product ID not found"));
        variant.setProduct(productId);
        variant.setVariantName(request.getVariantName());
        variant.setVariantValue(request.getVariantValue());
        variant.setPrice(request.getPrice());
        variant.setRefundable(request.getRefundable());
        repository.save(variant);
//        creating inventory with variant

        ProductInventory inventory = new ProductInventory();
        inventory.setQuantity(request.getInventoryRequest().getQuantity());
        inventory.setLocation(request.getInventoryRequest().getLocation());
//            inventory.setLastUpdated(request.getInventoryRequest().getLastUpdated());
//            ProductVariant variant1 = repository.findById(request.getInventoryRequest().getVariantId())
//                    .orElseThrow(() -> new ResourceNotFoundException("invalid variant ID"));
        inventory.setProduct(variant.getProduct());
        inventory.setProductVariant(variant);
        inventoryRepository.save(inventory);

        return ProductVariantResponse.builder()
                .message("Product variant created successfully")
                .build();
    }
}
