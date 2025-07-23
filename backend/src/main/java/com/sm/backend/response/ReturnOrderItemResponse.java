package com.sm.backend.response;

import com.sm.backend.model.Order;
import com.sm.backend.model.ReturnOrderItem;
import com.sm.backend.util.ReturnReason;
import com.sm.backend.util.ReturnStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
public class ReturnOrderItemResponse {
    private Long id;
    private Long orderId;
    private Long orderItemId;
    private Long productId;
    private Long productVariantId;
    private String productName;
    private String variantName;
    private String variantValue;
    private Double unitPrice;
    private Long returnQuantity;
    private Double refundAmount;
    private ReturnReason returnReason;
    private ReturnStatus returnStatus;


    public ReturnOrderItemResponse(ReturnOrderItem returnOrderItem) {
        this.id = returnOrderItem.getId();
        this.orderId = returnOrderItem.getOrder().getId();
        this.orderItemId = returnOrderItem.getOrderItem().getId();
        this.productId = returnOrderItem.getProduct().getId();
        this.productVariantId = returnOrderItem.getProductVariant().getId();
        this.productName = returnOrderItem.getProduct().getProductName();
        this.variantName = returnOrderItem.getProductVariant().getVariantName();
        this.variantValue = returnOrderItem.getProductVariant().getVariantValue();
        this.unitPrice = returnOrderItem.getUnitPrice();
        this.returnQuantity = returnOrderItem.getReturnQuantity();
        this.refundAmount = returnOrderItem.getRefundAmount();
        this.returnReason = returnOrderItem.getReturnReason();
        this.returnStatus = returnOrderItem.getReturnStatus();
    }
}
