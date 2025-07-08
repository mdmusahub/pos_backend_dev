package com.sm.backend.service;


import com.sm.backend.request.ReturnOrderRequest;
//import com.sm.backend.response.ReturnOrderItemResponse;

import java.util.List;

public interface ReturnOrderItemService {

    void CreateReturnOrder (ReturnOrderRequest request);
//    List<ReturnOrderItemResponse> findAll ();
//    ReturnOrderItemResponse findById (Long id);
}
