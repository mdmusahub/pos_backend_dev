package com.sm.backend.response;

import com.sm.backend.model.Order;
import com.sm.backend.model.ReturnOrder;
import com.sm.backend.util.ReturnReason;
import com.sm.backend.util.ReturnStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ReturnOrderResponse {
    private Long id;
    private OrderResponse orderId;
    private Long returnQuantity;
    private ReturnReason returnReason;
    private ReturnStatus returnStatus;
    private Double refundAmount;
    private LocalDateTime returnDate;


    public ReturnOrderResponse(ReturnOrder returnOrder) {
        this.id = returnOrder.getId();
        this.orderId = new OrderResponse(returnOrder.getOrderId());
        this.returnQuantity = returnOrder.getReturnQuantity();
        this.returnReason = returnOrder.getReturnReason();
        this.returnStatus = returnOrder.getReturnStatus();
        this.refundAmount = returnOrder.getRefundAmount();
        this.returnDate = returnOrder.getReturnDate();
    }
}
