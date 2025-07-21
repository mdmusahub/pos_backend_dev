//package com.sm.backend.response;
//
//import com.sm.backend.model.Order;
//import com.sm.backend.model.ReturnOrderItem;
//import com.sm.backend.util.ReturnReason;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.List;
//
//
//@Data
//@NoArgsConstructor
//public class ReturnOrderItemResponse {
//    private Long id;
//    private OrderResponse orderId;
//    private List<OrderItemResponse> orderItemResponseList;
//    private ProductResponse productId;
//    private ProductVariantResponse productVariantId;
//    private Long returnQuantity;
//    private Double unitPrice;
//    private Double refundAmount;
//    private OrderResponse requestedBy;
//
//    public ReturnOrderItemResponse(ReturnOrderItem returnOrderItem) {
//        this.id = returnOrderItem.getId();
//        this.orderId = new OrderResponse(returnOrderItem.getOrderId());
//        this.orderItemResponseList =
//        this.productId = new ProductResponse(returnOrderItem.getProductId());
//        this.productVariantId = new ProductVariantResponse(returnOrderItem.getProductVariantId());
//        this.returnQuantity = returnOrderItem.getReturnQuantity();
//        this.unitPrice = returnOrderItem.getUnitPrice();
//        this.refundAmount = returnOrderItem.getRefundAmount();
//        this.requestedBy = new OrderResponse(returnOrderItem.getRequestedBy());
//    }
//}
