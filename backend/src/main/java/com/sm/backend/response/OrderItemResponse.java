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
    private Order order;
    private Product product;
    private ProductVariant productVariant;
    private Long quantity;
    private Double unitPrice;
    private Double totalPrice;

    public OrderItemResponse(OrderItem item) {
        this.orderItemId = item.getOrderItemId();
        this.order = item.getOrder();
        this.product = item.getProduct();
        this.productVariant = item.getProductVariant();
        this.quantity = item.getQuantity();
        this.unitPrice = item.getUnitPrice();
        this.totalPrice = item.getTotalPrice();
    }
}
