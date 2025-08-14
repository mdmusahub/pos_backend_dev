package com.sm.backend.service;

import com.sm.backend.request.OrderRequest;
import com.sm.backend.response.OrderResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {
    ResponseEntity<?> createOrder(OrderRequest request) throws Exception;

    List<OrderResponse> getAll();

    OrderResponse getById(Long orderId);

    void delete(Long orderId);

    void updateOrder(Long id, OrderRequest request);
}
