package com.sm.backend.factory.product;

import com.sm.backend.mapper.ProductMapper;
import com.sm.backend.model.Product;
import com.sm.backend.repository.ProductRepository;
import com.sm.backend.request.ProductRequest;
import com.sm.backend.response.ProductResponse;
import com.sm.backend.util.RequestType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadAllProducts implements ProductOperation{

    private final ProductRepository repository;

    private final ProductMapper mapper;

    @Override
    public RequestType getRequestType() {
        return RequestType.READ_ALL;
    }

    @Override
    public ProductResponse execute(ProductRequest request) {

        List<Product> all = repository.findAll();
        return ProductResponse.builder()
                .products(mapper.toProductResponseList(all))
                .build();
    }
}
