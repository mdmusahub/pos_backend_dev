package com.sm.backend.request;

import com.sm.backend.model.Product;
import com.sm.backend.util.DiscountType;
import com.sm.backend.util.WaiverMode;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DiscountRequest {
    private String discountName;
//    private DiscountType discountType;
    private Long variantId;
    private WaiverMode waiverMode;
    private Double discountValue;
    private LocalDateTime endDateTime;
//    private Double minimumPrice;
//    private Long minimumQuantity;
}
