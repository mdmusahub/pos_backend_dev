package com.sm.backend.service;

import com.sm.backend.request.ReturnOrderRequest;
import com.sm.backend.response.ReturnOrderResponse;

import java.util.List;

public interface ReturnOrderService {

    void createReturnOrder (ReturnOrderRequest request);
    List<ReturnOrderResponse> findAll();
    ReturnOrderResponse findById (Long id);
}
