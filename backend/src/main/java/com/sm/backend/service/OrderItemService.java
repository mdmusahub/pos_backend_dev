package com.sm.backend.service;

import com.sm.backend.response.OrderItemResponse;

import java.util.List;

public interface OrderItemService {

    List<OrderItemResponse> getAll();

    OrderItemResponse getById(Long orderItemId);
}
