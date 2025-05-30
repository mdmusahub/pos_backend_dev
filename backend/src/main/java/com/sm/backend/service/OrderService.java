package com.sm.backend.service;

import com.sm.backend.request.OrderRequest;

public interface OrderService {
    void register(OrderRequest request);

    Object getAllOrders();

    Object findById(Long orderId);

    void deleteById(Long orderId);
}
