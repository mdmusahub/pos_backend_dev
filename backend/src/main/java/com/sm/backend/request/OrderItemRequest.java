package com.sm.backend.request;

import com.sm.backend.model.Order;
import com.sm.backend.model.Product;
import com.sm.backend.model.ProductVariant;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
public class OrderItemRequest {
//    private Long orderId;
    private Long variantId;
    private Long quantity;
//    private Double totalPrice;
}
