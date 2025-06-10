package com.sm.backend.service;

import com.sm.backend.request.OrderRequest;

public interface OrderService {
    void createOrder(OrderRequest request);

    Object getAll();

    Object getById(Long orderId);

    void delete(Long orderId);
}
