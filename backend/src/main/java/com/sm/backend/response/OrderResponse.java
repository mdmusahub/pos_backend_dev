package com.sm.backend.response;

import com.sm.backend.model.Customer;
import com.sm.backend.model.Order;
import com.sm.backend.model.OrderItem;
import com.sm.backend.utility.OrderStatus;
import com.sm.backend.utility.PaymentMode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderResponse {
    private Long orderID;
    private CustomerResponse customer;
    private String userPhoneNumber;
    private OrderStatus status;
    private Double discount;
    private Double tax;
    private Double totalAmount;
    private PaymentMode paymentMode;
    private String onlineAmount;
    private String cashAmount;
    private LocalDateTime orderDate;
    private LocalDateTime updatedAt;
    private List<OrderItemResponse> orderItems;

    public OrderResponse(Order order) {
        this.orderID = order.getOrderID();
        this.userPhoneNumber = order.getUserPhoneNumber();
        this.status = order.getStatus();
        this.discount = order.getDiscount();
        this.tax = order.getTax();
        this.totalAmount = order.getTotalAmount();
        this.paymentMode = order.getPaymentMode();
        this.onlineAmount = order.getOnlineAmount();
        this.cashAmount = order.getCashAmount();
        this.orderDate = order.getOrderDate();
        this.updatedAt = order.getUpdatedAt();
        this.orderItems = order.getOrderItems().stream().map(OrderItemResponse::new).toList();
        this.customer = new CustomerResponse(order.getCustomer());
    }


}
