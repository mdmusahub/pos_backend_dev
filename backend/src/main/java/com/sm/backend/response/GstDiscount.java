package com.sm.backend.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GstDiscount {
    private String gstPercentage;
    private Double gstAmount;
    private String discountPercentage;
    private Double discountAmount;
    private Double totalAmount;
}
