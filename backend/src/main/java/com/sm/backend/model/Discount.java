package com.sm.backend.model;

import com.sm.backend.util.DiscountType;
import com.sm.backend.util.WaiverMode;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long discountId;
    private String discountName;
    private DiscountType discountType;
    @ManyToOne
    private Product product;
    private WaiverMode waiverMode;
    private Double discountValue;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Boolean isActive;
    private Double minimumPrice;
    private Long minimumQuantity;

}
