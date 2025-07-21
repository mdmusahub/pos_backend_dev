package com.sm.backend.response;

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


    public ReturnOrderResponse(ReturnOrder order) {
        this.id = order.getId();
        this.orderId = new OrderResponse(order.getOrder());
        this.returnQuantity = order.getReturnQuantity();
        this.refundAmount = order.getRefundAmount();
        this.returnDate = order.getReturnDate();
    }
}
