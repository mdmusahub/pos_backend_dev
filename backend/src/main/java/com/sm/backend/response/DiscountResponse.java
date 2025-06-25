package com.sm.backend.response;

import com.sm.backend.model.Discount;
import com.sm.backend.model.Product;
import com.sm.backend.util.DiscountType;
import com.sm.backend.util.WaiverMode;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DiscountResponse {
    private Long discountId;
    private String discountName;
    private DiscountType discountType;
    private Product product;
    private WaiverMode waiverMode;
    private Double discountValue;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Boolean isActive;
    private Double minimumPrice;
    private Long minimumQuantity;

    public DiscountResponse(Discount discount) {
        this.discountId = discount.getDiscountId();
        this.discountName = discount.getDiscountName();
        this.discountType = discount.getDiscountType();
        this.discountValue = discount.getDiscountValue();
        this.endDateTime = discount.getEndDateTime();
        this.isActive = discount.getIsActive();
        this.minimumPrice = discount.getMinimumPrice();
        this.minimumQuantity = discount.getMinimumQuantity();
        this.product =discount.getProduct();
        this.startDateTime = discount.getStartDateTime();
        this.waiverMode = discount.getWaiverMode();
    }
}
