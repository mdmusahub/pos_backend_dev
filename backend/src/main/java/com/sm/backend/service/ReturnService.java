package com.sm.backend.service;

import com.sm.backend.request.ReturnOrderRequest;
import com.sm.backend.response.ReturnOrderResponse;

import java.util.List;

public interface ReturnService {

    void createReturnOrder (ReturnOrderRequest request);

    List<ReturnOrderResponse> findAll();

    ReturnOrderResponse getById(Long returnOrderId);

    void deleteById (Long id);
}
