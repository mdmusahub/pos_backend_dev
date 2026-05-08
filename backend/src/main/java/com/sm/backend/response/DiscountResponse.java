package com.sm.backend.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sm.backend.model.Discount;
import com.sm.backend.model.Product;
import com.sm.backend.model.ProductVariant;
import com.sm.backend.util.DiscountType;
import com.sm.backend.util.WaiverMode;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DiscountResponse {
    private Long discountId;
    private String discountName;
//    private DiscountType discountType;
    private ProductVariant variant;
    private WaiverMode waiverMode;
    private Double discountValue;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Boolean isActive;
    private String message;
    private List<DiscountResponse> discounts;
//    private Double minimumPrice;
//    private Long minimumQuantity;

    public DiscountResponse(Discount discount) {
        this.discountId = discount.getId();
        this.discountName = discount.getDiscountName();
//        this.discountType = discount.getDiscountType();
        this.discountValue = discount.getDiscountValue();
        this.endDateTime = discount.getEndDateTime();
        this.isActive = discount.getIsActive();
//        this.minimumPrice = discount.getMinimumPrice();
//        this.minimumQuantity = discount.getMinimumQuantity();
        this.variant =discount.getVariant();
        this.startDateTime = discount.getStartDateTime();
        this.waiverMode = discount.getWaiverMode();
    }
}
