package com.sm.backend.factory.product;

import com.sm.backend.request.ProductRequest;
import com.sm.backend.response.ProductResponse;
import com.sm.backend.util.RequestType;

public interface ProductOperation {

    RequestType getRequestType();

    ProductResponse execute(ProductRequest request);
}
