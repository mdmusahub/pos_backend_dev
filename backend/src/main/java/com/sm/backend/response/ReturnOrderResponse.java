package com.sm.backend.response;

import com.sm.backend.model.ReturnOrder;
import com.sm.backend.model.ReturnOrderItem;
import com.sm.backend.util.ReturnReason;
import com.sm.backend.util.ReturnStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.stylesheets.LinkStyle;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class ReturnOrderResponse {
    private Long id;
    private Long orderId;
    private LocalDateTime returnDate;
    private List<ReturnOrderItemResponse> returnOrderItem;
    private Long returnQuantity;
    private Double refundAmount;


    public ReturnOrderResponse(ReturnOrder returnOrder, List<ReturnOrderItem> returnOrderItems) {
        this.id = returnOrder.getId();
        this.orderId = returnOrder.getOrder().getId();
        this.returnDate = returnOrder.getReturnDate();
        this.returnOrderItem = returnOrderItems.stream().map(ReturnOrderItemResponse::new).toList();
        this.returnQuantity = returnOrder.getReturnQuantity();
        this.refundAmount = returnOrder.getRefundAmount();
    }
}
