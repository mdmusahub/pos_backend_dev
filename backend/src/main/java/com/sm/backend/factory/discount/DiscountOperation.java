package com.sm.backend.factory.discount;

import com.sm.backend.request.DiscountRequest;
import com.sm.backend.response.DiscountResponse;
import com.sm.backend.util.RequestType;

public interface DiscountOperation {

    RequestType getRequestType();

     DiscountResponse execute(DiscountRequest request);
}
