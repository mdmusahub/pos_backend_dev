package com.sm.backend.factory.productVariant;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.repository.ProductVariantRepository;
import com.sm.backend.request.ProductVariantRequest;
import com.sm.backend.response.ProductVariantResponse;
import com.sm.backend.util.RequestType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadVariantById implements VariantOperation{

    private final ProductVariantRepository repository;

    @Override
    public RequestType getRequestType() {
        return RequestType.READ_BY_ID;
    }

    @Override
    public ProductVariantResponse execute(ProductVariantRequest request) {

        return new ProductVariantResponse(repository.findById(
                request.getVariantId())
                .orElseThrow(()->new ResourceNotFoundException(
                        "invalid variant Id")));
    }
}
