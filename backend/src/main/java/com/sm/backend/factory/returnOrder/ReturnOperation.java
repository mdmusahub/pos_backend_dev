package com.sm.backend.factory.returnOrder;

import com.sm.backend.request.ReturnOrderRequest;
import com.sm.backend.response.ReturnOrderResponse;
import com.sm.backend.util.RequestType;

public interface ReturnOperation {
    RequestType getRequestType();

     ReturnOrderResponse execute(ReturnOrderRequest request);
}
