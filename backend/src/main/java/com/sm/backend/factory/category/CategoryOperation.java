package com.sm.backend.factory.category;

import com.sm.backend.request.CategoryRequest;
import com.sm.backend.response.CategoryResponse;
import com.sm.backend.util.RequestType;

public interface CategoryOperation {

    RequestType getRequestType();
    CategoryResponse execute(CategoryRequest request);

}
