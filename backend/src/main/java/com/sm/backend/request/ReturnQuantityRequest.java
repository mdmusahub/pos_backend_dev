package com.sm.backend.request;

import com.sm.backend.util.ReturnReason;
import com.sm.backend.util.ReturnStatus;
import lombok.Data;

@Data
public class ReturnQuantityRequest {
    private Long orderItemId;
    private Long returnQuantity;
    private ReturnStatus returnStatus;
    private ReturnReason returnReason;
}
