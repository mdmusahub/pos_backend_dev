package com.sm.backend.factory.product;

import com.sm.backend.exceptionalHandling.ProductAlreadyExistsException;
import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.model.Category;
import com.sm.backend.model.Product;
import com.sm.backend.model.ProductInventory;
import com.sm.backend.model.ProductVariant;
import com.sm.backend.repository.*;
import com.sm.backend.request.ProductRequest;
import com.sm.backend.request.ProductVariantRequest;
import com.sm.backend.response.ProductResponse;
import com.sm.backend.util.RequestType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreateProduct implements ProductOperation{

    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;
    private final ProductVariantRepository variantRepository;
    private final ProductInventoryRepository inventoryRepository;


    @Override
    public RequestType getRequestType() {
        return RequestType.CREATE;
    }

    @Override
    public ProductResponse execute(ProductRequest request) {
//       creating products
        Optional<Product> productByProductName = repository.findProductByProductName(request.getProductName());
        if (productByProductName.isPresent()) {
            throw new ProductAlreadyExistsException("the product " + request.getProductName() + " already exists");
        } else {
            Product product = new Product();
            product.setProductName(request.getProductName());
            product.setSku(request.getSku());
            product.setDescription(request.getDescription());
            Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Invalid Category ID"));
            product.setCategory(category);
            repository.save(product);

//        creating variants
            if (!request.getVariantRequests().isEmpty()) {
                for (ProductVariantRequest request1 : request.getVariantRequests()) {
                    ProductVariant variant = new ProductVariant();
                    variant.setVariantName(request1.getVariantName());
                    variant.setVariantValue(request1.getVariantValue());
                    variant.setPrice(request1.getPrice());
                    variant.setProduct(repository.findById(product.getId()).orElseThrow(() -> new ResourceNotFoundException("invalid product ID")));
                    variant.setRefundable(request1.getRefundable());
                    variantRepository.save(variant);
//            creating inventories
                    ProductInventory inventory = new ProductInventory();
                    inventory.setQuantity(request1.getInventoryRequest().getQuantity());
                    inventory.setLocation(request1.getInventoryRequest().getLocation());
//                   inventory.setLastUpdated(request1.getInventoryRequest().getLastUpdated());
                    inventory.setProduct(variant.getProduct());
                    inventory.setProductVariant(variant);
                    inventoryRepository.save(inventory);
                }
            }
        }
        return ProductResponse.builder()
                .message("product created successfully")
                .build();
    }
}
