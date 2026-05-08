package com.sm.backend.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sm.backend.model.ReturnOrder;
import com.sm.backend.model.ReturnOrderItem;
import com.sm.backend.util.ReturnReason;
import com.sm.backend.util.ReturnStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.stylesheets.LinkStyle;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReturnOrderResponse {
    private Long id;
    private Long orderId;
    private LocalDateTime returnDate;
    private List<ReturnOrderItemResponse> returnOrderItems;
    private Long returnQuantity;
    private Double refundAmount;
    private String message;
    private List<ReturnOrderResponse> returnOrders;


    public ReturnOrderResponse(ReturnOrder returnOrder, List<ReturnOrderItem> returnOrderItems) {
        this.id = returnOrder.getId();
        this.orderId = returnOrder.getOrder().getId();
        this.returnDate = returnOrder.getReturnDate();
        this.returnOrderItems =
                returnOrderItems.stream().map(ReturnOrderItemResponse::new).toList();
        this.returnQuantity = returnOrder.getReturnQuantity();
        this.refundAmount = returnOrder.getRefundAmount();
    }
}
