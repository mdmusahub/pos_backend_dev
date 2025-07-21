package com.sm.backend.request;


import com.sm.backend.util.ReturnReason;
import com.sm.backend.util.ReturnStatus;
import lombok.Data;

import java.util.List;

@Data
public class ReturnOrderRequest {
    private Long orderId;
    private List<ReturnQuantityRequest> returnQuantityRequestList;
}
