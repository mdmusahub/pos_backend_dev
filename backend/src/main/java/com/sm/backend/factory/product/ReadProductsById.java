package com.sm.backend.factory.product;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.mapper.ProductMapper;
import com.sm.backend.model.Product;
import com.sm.backend.repository.ProductRepository;
import com.sm.backend.request.ProductRequest;
import com.sm.backend.response.ProductResponse;
import com.sm.backend.util.RequestType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadProductsById implements ProductOperation {

    private final ProductRepository repository;

    private final ProductMapper mapper;

    @Override
    public RequestType getRequestType() {
        return RequestType.READ_BY_ID;
    }

    @Override
    public ProductResponse execute(ProductRequest request) {
        Product product = repository.findById(request.getProductId()).orElseThrow(() -> new ResourceNotFoundException("invalid " + "Product ID"));
        return mapper.toProductResponse(product);
    }
}
