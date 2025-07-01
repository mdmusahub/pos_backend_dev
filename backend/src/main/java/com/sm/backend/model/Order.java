package com.sm.backend.model;

import com.sm.backend.utility.OrderStatus;
import com.sm.backend.utility.PaymentMode;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "ordering")
public class Order {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long orderID;
@ManyToOne
private Customer customer;
@OneToMany(fetch = FetchType.EAGER)
private List<OrderItem> orderItems;
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
@CreationTimestamp
private LocalDateTime orderDate;
private LocalDateTime updatedAt;
}
