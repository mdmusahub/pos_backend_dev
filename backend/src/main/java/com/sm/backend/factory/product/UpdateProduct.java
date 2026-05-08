package com.sm.backend.factory.product;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.model.Category;
import com.sm.backend.model.Product;
import com.sm.backend.repository.*;
import com.sm.backend.request.ProductRequest;
import com.sm.backend.response.ProductResponse;
import com.sm.backend.util.RequestType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
@Log4j2
public class UpdateProduct implements ProductOperation{

    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;

    @Override
    public RequestType getRequestType() {
        return RequestType.UPDATE;
    }

    @Override
    public ProductResponse execute(ProductRequest request) {
        Product product =
                repository.findById(request.getProductId()).orElseThrow(()
                        -> new ResourceNotFoundException("invalid product ID"));

        if (request.getProductName() != null) {
            product.setProductName(request.getProductName());
        }
        if (request.getDescription() != null) {
            product.setDescription(request.getDescription());
        }
        if (request.getSku() != null) {
            product.setSku(request.getSku());
        }
        product.setUpdatedAt(LocalDateTime.now());

        if (request.getCategoryId() != null) {
            Category category = categoryRepository.
                    findById(request.getCategoryId()).orElseThrow((
            ) -> new ResourceNotFoundException("invalid category ID"));
            product.setCategory(category);
        }
        repository.save(product);

        return ProductResponse.builder()
                .message("Product updated successfully")
                .build();
    }
}
