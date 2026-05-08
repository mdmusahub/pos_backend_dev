package com.sm.backend.factory.tax;

import com.sm.backend.request.TaxRequest;
import com.sm.backend.response.TaxResponse;
import com.sm.backend.util.RequestType;

public interface TaxOperation {

    RequestType getRequestType();

    TaxResponse execute(TaxRequest request);
}
