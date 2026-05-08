package com.sm.backend.factory.customer;

import com.sm.backend.request.CategoryRequest;
import com.sm.backend.request.CustomerRequest;
import com.sm.backend.response.CustomerResponse;
import com.sm.backend.util.RequestType;

public interface CustomerOperation {
    RequestType getRequestType();

     CustomerResponse execute(CustomerRequest request);
}
