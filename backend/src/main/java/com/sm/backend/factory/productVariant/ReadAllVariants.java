package com.sm.backend.factory.productVariant;

import com.sm.backend.model.ProductVariant;
import com.sm.backend.repository.ProductVariantRepository;
import com.sm.backend.request.ProductVariantRequest;
import com.sm.backend.response.ProductVariantResponse;
import com.sm.backend.util.RequestType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadAllVariants implements VariantOperation{

    private final ProductVariantRepository repository;

    @Override
    public RequestType getRequestType() {
        return RequestType.READ_ALL;
    }

    @Override
    public ProductVariantResponse execute(ProductVariantRequest request) {

        List<ProductVariant> all = repository.findAll();

        return ProductVariantResponse.builder()
                .variants(
                        all.stream().map(ProductVariantResponse::new).toList())
                .build();
    }
}
