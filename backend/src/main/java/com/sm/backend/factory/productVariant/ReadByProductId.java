package com.sm.backend.factory.productVariant;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.model.Product;
import com.sm.backend.model.ProductVariant;
import com.sm.backend.repository.ProductRepository;
import com.sm.backend.repository.ProductVariantRepository;
import com.sm.backend.request.ProductVariantRequest;
import com.sm.backend.response.ProductVariantResponse;
import com.sm.backend.util.RequestType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadByProductId implements VariantOperation{

    private final ProductRepository productRepository;
    private final ProductVariantRepository repository;
    @Override
    public RequestType getRequestType() {
        return RequestType.READ_BY_PRODUCT_ID;
    }

    @Override
    public ProductVariantResponse execute(ProductVariantRequest request) {
        Product product=
                productRepository.findById(request.getProductId()).orElseThrow(()-> new ResourceNotFoundException("product id not found"));
        List<ProductVariant> productVariants=
                repository.getAllVariantsByProductId(request.getProductId());
        return ProductVariantResponse.builder()
                .variants(productVariants.stream().map(ProductVariantResponse::new).toList())
                .build();
    }
}
