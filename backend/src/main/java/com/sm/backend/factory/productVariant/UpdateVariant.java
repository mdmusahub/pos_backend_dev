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

@Service
@RequiredArgsConstructor
public class UpdateVariant implements VariantOperation{

    private final ProductVariantRepository repository;
    private final ProductRepository productRepository;
    @Override
    public RequestType getRequestType() {
        return RequestType.UPDATE;
    }

    @Override
    public ProductVariantResponse execute(ProductVariantRequest request) {

        ProductVariant variant =
                repository.findById(request.getVariantId()).orElseThrow(()->new ResourceNotFoundException("invalid variant ID"));
        if (request.getProductId()!=null){
            Product product = productRepository.findById(request.getProductId()).orElseThrow(()->new ResourceNotFoundException("invalid product ID"));
            variant.setProduct(product);
        }

        if (request.getVariantName()!=null){
            variant.setVariantName(request.getVariantName());
        }
        if (request.getVariantValue()!=null){
            variant.setVariantValue(request.getVariantValue());
        }
        if (request.getPrice()!=null){
            variant.setPrice(request.getPrice());
        }
        if(request.getRefundable() != null){
            variant.setRefundable(request.getRefundable());
        }
        repository.save(variant);
        return ProductVariantResponse.builder()
                .message("Updated updated successfully with ID " + request.getVariantId())
                .build();
    }
}
