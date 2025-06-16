package com.sm.backend.service;

import com.sm.backend.request.OrderRequest;
import com.sm.backend.response.OrderResponse;

import java.util.List;

public interface OrderService {
    void createOrder(OrderRequest request);

    List<OrderResponse> getAll(Integer pageNumber,Integer pageSize, String sortby,String sortDir);

    OrderResponse getById(Long orderId);

    void delete(Long orderId);
}
