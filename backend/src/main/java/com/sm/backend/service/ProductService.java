package com.sm.backend.service;


import com.sm.backend.request.ProductRequest;

public interface ProductService {

    void register(ProductRequest request);

    Object findById(Long productId);

    Object findall(Integer pageNumber, Integer pageSize, String sortby, String sortDir);

    Object updateDetails(ProductRequest request, Long productId);


    void delete(Long productId);
}
