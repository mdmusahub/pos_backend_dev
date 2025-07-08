package com.sm.backend.response;

import com.sm.backend.model.Order;
import com.sm.backend.model.OrderItem;
import com.sm.backend.model.Product;
import com.sm.backend.model.ProductVariant;
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
    private Long quantity;
    private Double unitPrice;
    private Double totalPrice;

    public OrderItemResponse(OrderItem item) {
        this.orderItemId = item.getOrderItemId();
        this.productId = item.getProduct().getProductId();
        this.productName = item.getProduct().getProductName();
        this.variantId = item.getProductVariant().getProductVariantId();
        this.variantName = item.getProductVariant().getVariantName();
        this.variantValue = item.getProductVariant().getVariantValue();
        this.quantity = item.getQuantity();
        this.unitPrice = item.getUnitPrice();
        this.totalPrice = item.getTotalPrice();
    }
}
