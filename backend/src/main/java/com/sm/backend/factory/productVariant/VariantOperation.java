package com.sm.backend.factory.productVariant;

import com.sm.backend.request.ProductVariantRequest;
import com.sm.backend.response.ProductVariantResponse;
import com.sm.backend.response.productDetailsResponses.VariantResponse;
import com.sm.backend.util.RequestType;

public interface VariantOperation {

    RequestType getRequestType();

     ProductVariantResponse execute(ProductVariantRequest request);
}
