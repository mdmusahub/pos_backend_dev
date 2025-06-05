package com.sm.backend.service;


import com.sm.backend.request.ProductRequest;

public interface ProductService {

    void createProduct(ProductRequest request);

    Object getById(Long productId);

    Object getall(Integer pageNumber, Integer pageSize, String sortby, String sortDir);

    Object update(ProductRequest request, Long productId);


    void delete(Long productId);
}
