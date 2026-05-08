package com.sm.backend.factory.order;

import com.sm.backend.request.OrderRequest;
import com.sm.backend.response.OrderResponse;
import com.sm.backend.util.RequestType;

public interface OrderOperation {
    RequestType getRequestType();

     OrderResponse execute(OrderRequest request);
}
