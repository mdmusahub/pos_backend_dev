package com.sm.backend.factory.product;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.model.Product;
import com.sm.backend.model.ProductInventory;
import com.sm.backend.model.ProductVariant;
import com.sm.backend.repository.*;
import com.sm.backend.request.ProductRequest;
import com.sm.backend.request.productUpdateReq.VariantUpdateRequest;
import com.sm.backend.response.ProductResponse;
import com.sm.backend.util.RequestType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class UpdateProductDetails implements ProductOperation{

    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;
    private final ProductVariantRepository variantRepository;
    private final ProductInventoryRepository inventoryRepository;
    private final OrderItemRepository orderItemRepository;


    @Override
    public RequestType getRequestType() {
        return RequestType.UPDATE_DETAILS;
    }

    @Override
    public ProductResponse execute(ProductRequest request) {
        Product product =
                repository.findById(request.getProductId())
                        .orElseThrow(()->new ResourceNotFoundException(
                                "invalid id."));
        if(request.getProductName()!=null){
            product.setProductName(request.getProductName());
        }
        if(request.getSku()!=null){
            product.setSku(request.getSku());
        }
        if(request.getDescription()!=null){
            product.setDescription(request.getDescription());
        }
        if(request.getCategoryId()!=null){
            product.setCategory(
                    categoryRepository.findById(
                            request.getCategoryId())
                            .orElseThrow(()->new ResourceNotFoundException(
                                    "invalid category id.")));
        }

        product.setUpdatedAt(LocalDateTime.now());
        List<VariantUpdateRequest> variants=request.getVariantUpdateRequests();

        for(VariantUpdateRequest vi:variants){
            ProductVariant productVariant=variantRepository.findById(vi.getVariantId()).orElseThrow(()->new ResourceNotFoundException("invalid variant id."));
            if(vi.getVariantName()!=null){
                productVariant.setVariantName(vi.getVariantName());
            }
            if(vi.getVariantValue()!=null){
                productVariant.setVariantValue(vi.getVariantValue());
            }
            if(vi.getVariantPrice()!=null){
                productVariant.setPrice(vi.getVariantPrice());
            }

            ProductInventory inventory = inventoryRepository.findProductInventoryByProductVariant(productVariant);
            inventory.setLastUpdated(LocalDateTime.now());
            if(vi.getQuantity()!=null){
                inventory.setQuantity(vi.getQuantity());
            }
            if(vi.getLocation()!=null){
                inventory.setLocation(vi.getLocation());
            }
            inventoryRepository.save(inventory);
            variantRepository.save(productVariant);
        }
        repository.save(product);

        return ProductResponse.builder()
                .message("Product details updated successfully.")
                .build();
    }
}
