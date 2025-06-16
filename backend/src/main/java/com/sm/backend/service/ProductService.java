package com.sm.backend.service;


import com.sm.backend.request.ProductRequest;
import com.sm.backend.response.ProductResponse;

import java.util.List;

public interface ProductService {

    void createProduct(ProductRequest request);

    Object getById(Long productId);

    List<ProductResponse> getAll(Integer pageNumber, Integer pageSize, String sortby, String sortDir);

    Object update(ProductRequest request, Long productId);


    void delete(Long productId);
}
