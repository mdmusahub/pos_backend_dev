package com.sm.backend.model;

import com.sm.backend.utility.OrderStatus;
import com.sm.backend.utility.PaymentMode;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "ordering")
public class Order {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long orderID;
private String userPhoneNumber;
@Enumerated(EnumType.STRING)
private OrderStatus status;
private Double discount;
private  Double tax;
private  Double totalAmount;
@Enumerated(EnumType.STRING)
private PaymentMode paymentMode;
private String onlineAmount;
private String cashAmount;
private LocalDateTime orderDate;
private LocalDateTime updatedAt;
}
