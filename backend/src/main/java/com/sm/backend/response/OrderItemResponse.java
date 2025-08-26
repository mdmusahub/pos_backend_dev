package com.sm.backend.response;

import com.sm.backend.model.*;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderItemResponse {
    private Long orderItemId;
    private Long productId;
    private String productName;
    private Long variantId;
    private String variantName;
    private String variantValue;
    private Boolean refundable;
    private Long quantity;
    private Double unitPrice;
    private Double totalPrice;

    public OrderItemResponse(OrderItem item) {
        this.orderItemId = item.getId();
        this.productId = item.getProduct().getId();
        this.productName = item.getProduct().getProductName();
        this.variantId = item.getProductVariant().getId();
        this.variantName = item.getProductVariant().getVariantName();
        this.variantValue = item.getProductVariant().getVariantValue();
        this.refundable = item.getProductVariant().getRefundable();
        this.quantity = item.getQuantity();
        this.unitPrice = item.getUnitPrice();
        this.totalPrice = item.getTotalPrice();
    }
}
