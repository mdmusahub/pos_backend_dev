package com.sm.backend.request;

import com.sm.backend.model.OrderItem;
import com.sm.backend.model.Product;
import com.sm.backend.model.ProductVariant;
import com.sm.backend.utility.OrderStatus;
import com.sm.backend.utility.PaymentMode;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.LifecycleState;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderRequest {
    private String userPhoneNumber;
    private OrderStatus status;
//    private Double discount;
//    private  Double tax;
    private PaymentMode paymentMode;
    private String onlineAmount;
    private String cashAmount;
//    private LocalDateTime orderDate;
//    private LocalDateTime updatedAt;
   private List<OrderItemRequest>orderItemRequests;
}

