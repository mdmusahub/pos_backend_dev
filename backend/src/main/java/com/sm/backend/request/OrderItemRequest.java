package com.sm.backend.request;

import com.sm.backend.model.Order;
import com.sm.backend.model.Product;
import com.sm.backend.model.ProductVariant;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
public class OrderItemRequest {
    private Long orderId;
    private Long productId;
    private Long variantId;
    private Integer quantity;
    private Double unitPrice;
    private Double totalPrice;
}
